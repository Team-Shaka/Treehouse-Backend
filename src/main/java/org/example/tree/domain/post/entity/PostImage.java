package org.example.tree.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String imageUrl;

        @JoinColumn(name = "postId")
        @ManyToOne(fetch = FetchType.LAZY)
        @Setter
        private Post post;

}
