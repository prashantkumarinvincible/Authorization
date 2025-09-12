package com.example.entitlements.service;

import com.example.entitlements.entity.*;
import com.example.entitlements.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EntitlementService {

    private final UserService userService;
    private final RefManagedSegmentRepository refManagedSegmentRepository;
    private final InitiativeRepository initiativeRepository;
    private final InitiativeOwnerRepository initiativeOwnerRepository;
    private final InitiativeContributorRepository initiativeContributorRepository;

    /**
     * Generic permission entry point used by aspect.
     * paramValue can be null or an id/object depending on parameterType.
     */
    public boolean hasPermission(String soeId, String permissionName, Object paramValue, String parameterType) {
        if (soeId == null) return false;
        UserProfile user = userService.loadUserWithRolesAndPermissions(soeId);

        // Check base role -> permission mapping via loaded roles -> accessRole -> permissions
        boolean baseAllowed = user.getRoles().stream()
                .filter(ur -> ur.getRole() != null)
                .flatMap(ur -> ur.getRole().getPermissions().stream())
                .anyMatch(p -> p.getName().equalsIgnoreCase(permissionName));

        if (!baseAllowed) return false;

        // If no special parameter, base permission is enough
        if (parameterType == null || parameterType.isBlank() || paramValue == null) {
            return true;
        }

        if ("INITIATIVE".equalsIgnoreCase(parameterType)) {
            Long initiativeId;
            if (paramValue instanceof Long) initiativeId = (Long) paramValue;
            else if (paramValue instanceof Integer) initiativeId = ((Integer) paramValue).longValue();
            else if (paramValue instanceof String) initiativeId = Long.valueOf((String) paramValue);
            else return false;
            return checkInitiativeAccess(user, initiativeId, permissionName);
        }

        if ("MANAGED_SEGMENT".equalsIgnoreCase(parameterType)) {
            String msId = String.valueOf(paramValue);
            return checkManagedSegmentAccess(user, msId);
        }

        // default allow if unknown parameter type and baseAllowed
        return true;
    }

    /**
     * Implements the Owner / Contributor rules described in user story.
     */
    public boolean checkInitiativeAccess(UserProfile user, Long initiativeId, String permissionName) {
        Optional<Initiative> initOpt = initiativeRepository.findById(initiativeId);
        if (initOpt.isEmpty()) return false;
        Initiative init = initOpt.get();
        String initMsId = init.getManagedSegmentId();
        if (initMsId == null) return false;

        Optional<RefManagedSegmentView> initSegOpt = refManagedSegmentRepository.findById(initMsId);
        if (initSegOpt.isEmpty()) return false;
        RefManagedSegmentView initSeg = initSegOpt.get();

        // user managed segments from cached user
        Set<String> userMsIds = user.getRoles().stream()
                .filter(ur -> ur.getHierarchySegments() != null)
                .flatMap(ur -> ur.getHierarchySegments().stream())
                .map(UserRoleHierarchySegment::getManagedSegmentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (userMsIds.isEmpty()) return false;

        // get init level2 ancestor
        String initLevel2 = getAncestorAtLevel(initSeg.getHierarchyString(), 2);
        // initiative must be level >=3 for owner/contrib rules (per user story)
        Integer initLevel = initSeg.getSegmentLvl() == null ? 0 : initSeg.getSegmentLvl();

        // VIEW_INITIATIVE
        if ("VIEW_INITIATIVE".equalsIgnoreCase(permissionName)) {
            if (initLevel >= 3 && initLevel2 != null) {
                for (String uMs : userMsIds) {
                    Optional<RefManagedSegmentView> usegOpt = refManagedSegmentRepository.findById(uMs);
                    if (usegOpt.isEmpty()) continue;
                    String userLevel2 = getAncestorAtLevel(usegOpt.get().getHierarchyString(), 2);
                    if (userLevel2 != null && userLevel2.equals(initLevel2)) {
                        // Exclude initiatives at level 2 itself per requirement
                        if (initSeg.getSegmentLvl() != null && initSeg.getSegmentLvl() == 2) continue;
                        return true;
                    }
                }
            }
            return false;
        }

        // EDIT/CREATE initiative
        if ("EDIT_INITIATIVE".equalsIgnoreCase(permissionName) || "CREATE_INITIATIVE".equalsIgnoreCase(permissionName)) {
            // Owner: must be present in owner table for that initiative and level rules: edit/create only within level 3
            boolean isOwner = initiativeOwnerRepository.existsByInitiativeIdAndOwnerId(initiativeId, user.getSoeId());
            if (isOwner && initLevel == 3) {
                // level2 match
                for (String uMs : userMsIds) {
                    Optional<RefManagedSegmentView> usegOpt = refManagedSegmentRepository.findById(uMs);
                    if (usegOpt.isEmpty()) continue;
                    String userLevel2 = getAncestorAtLevel(usegOpt.get().getHierarchyString(), 2);
                    if (userLevel2 != null && userLevel2.equals(initLevel2)) return true;
                }
            }
            // Contributor: can edit if contributor and level >=3 and level2 match
            boolean isContributor = initiativeContributorRepository.existsByInitiativeIdAndContributorId(initiativeId, user.getSoeId());
            if (isContributor && initLevel >= 3) {
                for (String uMs : userMsIds) {
                    Optional<RefManagedSegmentView> usegOpt = refManagedSegmentRepository.findById(uMs);
                    if (usegOpt.isEmpty()) continue;
                    String userLevel2 = getAncestorAtLevel(usegOpt.get().getHierarchyString(), 2);
                    if (userLevel2 != null && userLevel2.equals(initLevel2)) return true;
                }
            }
            return false;
        }

        return false;
    }

    public boolean checkManagedSegmentAccess(UserProfile user, String msId) {
        // user can access managed segment if any of user's level2 matches msId's level2 and ms level >=3
        Optional<RefManagedSegmentView> targetOpt = refManagedSegmentRepository.findById(msId);
        if (targetOpt.isEmpty()) return false;
        RefManagedSegmentView target = targetOpt.get();
        if (target.getSegmentLvl() == null || target.getSegmentLvl() < 3) return false;
        String targetLevel2 = getAncestorAtLevel(target.getHierarchyString(), 2);
        if (targetLevel2 == null) return false;

        Set<String> userLevel2 = user.getRoles().stream()
                .filter(ur -> ur.getHierarchySegments() != null)
                .flatMap(ur -> ur.getHierarchySegments().stream())
                .map(UserRoleHierarchySegment::getManagedSegmentId)
                .map(id -> refManagedSegmentRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(RefManagedSegmentView::getHierarchyString)
                .map(h -> getAncestorAtLevel(h, 2))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        return userLevel2.contains(targetLevel2);
    }

    // helper
    private String getAncestorAtLevel(String hierarchyString, int level) {
        if (hierarchyString == null) return null;
        String[] parts = hierarchyString.split("/");
        if (level <= 0 || level > parts.length) return null;
        return parts[level - 1];
    }

    public String getCurrentSoeId() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) return null;
            Object p = auth.getPrincipal();
            if (p instanceof UserDetails) return ((UserDetails)p).getUsername();
            return p == null ? null : p.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
