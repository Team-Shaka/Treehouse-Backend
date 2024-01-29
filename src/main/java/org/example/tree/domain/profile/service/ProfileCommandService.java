package org.example.tree.domain.profile.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileCommandService {
    private final ProfileRepository profileRepository;
    public void createProfile(Profile profile) {
        profileRepository.save(profile);
    }
}
