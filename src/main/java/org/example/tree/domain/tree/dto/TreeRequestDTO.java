package org.example.tree.domain.tree.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeRequestDTO {
    @Getter
    public static class createTree {
        private String treeName;
    }

    @Getter
    public static class registerTreeMember {
        private Long treeId;
        private String userId;
        private String memberName; //트리에서 사용할 닉네밈
        private String profileImage; //트리에서 사용할 프로필 이미지
    }
}
