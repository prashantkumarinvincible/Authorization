package com.example.entitlements.repository;

import com.example.entitlements.entity.RolePermissionMap;
import com.example.entitlements.entity.RolePermissionMapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionMapRepository extends JpaRepository<RolePermissionMap, RolePermissionMapId> {}
