package org.example.tree.domain.invitation.repository;

import org.example.tree.domain.invitation.entity.Invitation;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findAllByPhone(String phone);

    Optional<Invitation> findByPhoneAndTree(String phone, Tree tree);
}
