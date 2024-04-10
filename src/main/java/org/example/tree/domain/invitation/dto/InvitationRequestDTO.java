package org.example.tree.domain.invitation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationRequestDTO {

    @Getter
    public static class sendInvitation {
        private Long senderId;
        private Long treehouseId;
        private String phoneNumber;
    }

    @Getter
    public static class inviteMember {
        private Long senderId;
        private Long treeId;
        private Long targetUserId;
    }

    @Getter
    public static class acceptInvitation {
        private Long invitationId;
        private Boolean isAccepted;
    }

    @Getter
    public static class getInvitation {
        private Long invitationId;
    }

    @Getter
    public static class rejectInvitation {
        private Long invitationId;
        private Boolean isAccepted;
    }
}
