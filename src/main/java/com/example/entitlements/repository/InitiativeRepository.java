package com.example.entitlements.repository;

import com.example.entitlements.entity.Initiative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
}
