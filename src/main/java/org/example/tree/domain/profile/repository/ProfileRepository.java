package org.example.tree.domain.profile.repository;

import org.example.tree.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
