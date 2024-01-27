package org.example.tree.domain.tree.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.tree.common.BaseDateTimeEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tree extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer treeSize; //트리 규모(총 인원)

}
