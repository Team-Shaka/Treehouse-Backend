package org.example.tree.domain.invitation.repository;

import org.example.tree.domain.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
