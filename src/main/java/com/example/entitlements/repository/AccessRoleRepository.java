package com.example.entitlements.repository;

import com.example.entitlements.entity.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRoleRepository extends JpaRepository<AccessRole, Long> {}
