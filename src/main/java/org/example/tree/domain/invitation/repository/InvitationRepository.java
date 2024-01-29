package org.example.tree.domain.invitation.repository;

import org.example.tree.domain.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findAllByPhone(String phone);
}
