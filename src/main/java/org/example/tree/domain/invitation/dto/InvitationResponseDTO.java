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
        private Boolean isAccepted;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class rejectInvitation {
        private Long treeId;
        private Boolean isAccepted;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getInvitation {
        private Long invitationId;
        private String treehouseName;
        private String senderName;
        private String senderProfileImageUrl;
        private Integer treehouseSize;
        private List<String> treehouseMemberProfileImages;
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
