package com.example.entitlements.repository;

import com.example.entitlements.entity.UserProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    @EntityGraph(attributePaths = { "roles.role.permissions", "roles.hierarchySegments" })
    Optional<UserProfile> findBySoeIdAndIsActive(String soeId, Integer isActive);
}
