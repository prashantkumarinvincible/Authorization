package com.example.entitlements.repository;

import com.example.entitlements.entity.UserRoleHierarchySegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleHierarchySegmentRepository extends JpaRepository<UserRoleHierarchySegment, Long> {}
