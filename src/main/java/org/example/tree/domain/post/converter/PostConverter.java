package org.example.tree.domain.post.converter;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.entity.PostImage;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostConverter {

    public Post toPost(String content, Profile profile) {
        return Post.builder()
                .content(content)
                .reactionCount(0)
                .profile(profile)
                .tree(profile.getTree())
                .commentCount(0)
                .build();
    }


    public PostResponseDTO.createPost toCreatePost(Post savedPost) {
        List<String> imageUrls = savedPost.getPostImages().stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());
        return PostResponseDTO.createPost.builder()
                .postId(savedPost.getId())
                .build();
    }

    public PostResponseDTO.getFeed toGetFeed(Tree tree, List<PostResponseDTO.getPost> posts) {
        return PostResponseDTO.getFeed.builder()
                .treeId(tree.getId())
                .treeName(tree.getName())
                .posts(posts)
                .build();
    }

    public PostResponseDTO.getPost toGetPost(Post post, int branchDegree, List<ReactionResponseDTO.getReaction> reactions) {
        // PostImage 객체 리스트에서 이미지 URL 추출
        List<String> imageUrls = post.getPostImages().stream()
                .map(PostImage::getImageUrl)
                .collect(Collectors.toList());

        return PostResponseDTO.getPost.builder()
                .postId(post.getId())
                .authorId(post.getProfile().getId())
                .profileImageUrl(post.getProfile().getProfileImageUrl())
                .memberName(post.getProfile().getMemberName())
                .branchDegree(branchDegree)
                .content(post.getContent())
                .postImageUrls(imageUrls)
                .reactions(reactions)
                .commentCount(post.getCommentCount())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public List<PostImage> toPostImages(List<String> images) throws Exception {
        List<PostImage> postImages = new ArrayList<>();
        for(String imageUrl :images) {
            if (imageUrl != null) {// 이미지 업로드 후 URL 획득
                PostImage postImage = PostImage.builder()
                        .imageUrl(imageUrl) // PostImage 엔티티에는 imageUrl 필드가 있어야 합니다.
                        .build();
                postImages.add(postImage);
            }
        }
        return postImages;
    }
}
