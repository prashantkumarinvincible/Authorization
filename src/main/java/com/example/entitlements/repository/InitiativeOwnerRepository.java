package com.example.entitlements.repository;

import com.example.entitlements.entity.InitiativeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeOwnerRepository extends JpaRepository<InitiativeOwner, Long> {
    boolean existsByInitiativeIdAndOwnerId(Long initiativeId, String ownerId);
}
