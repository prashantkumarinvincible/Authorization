package com.example.entitlements.repository;

import com.example.entitlements.entity.InitiativeContributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeContributorRepository extends JpaRepository<InitiativeContributor, Long> {
    boolean existsByInitiativeIdAndContributorId(Long initiativeId, String contributorId);
}
