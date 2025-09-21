package com.example.entitlements.service;

import com.example.entitlements.entity.UserProfile;
import com.example.entitlements.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserProfileRepository userRepo;

    @Cacheable(value = "userCache", key = "#soeId")
    public UserProfile loadUserWithRolesAndPermissions(String soeId) {
        return userRepo.findBySoeIdAndIsActive(soeId, 1)
                .orElseThrow(() -> new RuntimeException("User not found or inactive"));
    }

     public List<UserProfile> getUsers(Long defaultManagedSegmentId,
                                      Long roleId,
                                      Integer level,
                                      String searchString) {
        // Get hierarchy string for the given managed segment
        ManagedSegment segment = managedSegmentRepository.findById(defaultManagedSegmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid managed segment id"));

        String[] parts = segment.getHierarchyString().split("/");
        // Build partial string up to "level"
        String partialHierarchy = Arrays.stream(parts)
                .filter(p -> !p.isBlank())
                .limit(level)
                .collect(Collectors.joining("/", "/", ""));

        return userProfileRepository.findUsersByRoleAndManagedSegment(
                roleId, defaultManagedSegmentId, partialHierarchy, searchString);
    }
}
