package org.example.tree.domain.member.repository;

import org.example.tree.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByPhone(String phone);
}
