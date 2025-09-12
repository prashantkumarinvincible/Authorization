package com.example.entitlements.repository;

import com.example.entitlements.entity.RefManagedSegmentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefManagedSegmentRepository extends JpaRepository<RefManagedSegmentView, String> {
}
