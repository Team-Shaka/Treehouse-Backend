package org.example.tree.domain.profile.repository;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberAndTree(Member member, Tree tree);


    List<Profile> findAllByMember_Id(Long memberId);

    List<Profile> findAllByTree(Tree tree);

    @Query("SELECT p FROM Profile p WHERE p.member = :member AND p.isActive = TRUE")
    Optional<Profile> findCurrentProfile(@Param("member") Member member);

    @Query("SELECT COUNT(p) = 0 FROM Profile p WHERE p.member = :member AND p.isActive = TRUE")
    boolean existsActiveProfileByMember(@Param("member") Member member);
}
