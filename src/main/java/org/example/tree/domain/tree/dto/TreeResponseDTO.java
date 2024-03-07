package org.example.tree.domain.tree.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getTree {
        private String treeName;
        private Integer treeSize;
        private List<String> treeMemberProfileImages;
        private Boolean isSelected;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class shiftTree {
        private Long treeId;
    }
}
