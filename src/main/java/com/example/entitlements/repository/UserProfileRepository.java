package com.example.entitlements.repository;

import com.example.entitlements.entity.UserProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    @EntityGraph(attributePaths = { "roles.role.permissions", "roles.hierarchySegments" })
    Optional<UserProfile> findBySoeIdAndIsActive(String soeId, Integer isActive);

    @Query("""
        SELECT DISTINCT u
        FROM UserProfile u
        JOIN u.roles ur
        JOIN ur.hierarchies urh
        JOIN urh.managedSegment ms
        WHERE ur.accessRoleId = :roleId
          AND u.defaultManagedSegmentId = :defaultManagedSegmentId
          AND u.isActive = 1
          AND ms.hierarchyString LIKE CONCAT(:partialHierarchy, '%')
          AND (
              LOWER(u.fullName) LIKE LOWER(CONCAT('%', :searchString, '%'))
              OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchString, '%'))
          )
        """)
    List<UserProfile> findUsersByRoleAndManagedSegment(
        @Param("roleId") Long roleId,
        @Param("defaultManagedSegmentId") Long defaultManagedSegmentId,
        @Param("partialHierarchy") String partialHierarchy,
        @Param("searchString") String searchString
    );
}
