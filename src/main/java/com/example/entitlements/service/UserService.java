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
}
