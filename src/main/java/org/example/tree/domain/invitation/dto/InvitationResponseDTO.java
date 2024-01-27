package org.example.tree.domain.invitation.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvitationResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class sendInvitation {
        private Integer availableInvitation;
        private Boolean isNewUser;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class acceptInvitation {
        private Long treeId;
        private Boolean isAccept;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getInvitation {
        private Long invitationId;
        private String treeName;
        private String senderName;
        private Integer treeSize;
        private List<String> treeMemberProfileImages;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getAvailableInvitation {
        private Integer availableInvitation;
        private Double activeRate;
    }
}
