package org.example.tree.domain.post.converter;

import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.entity.PostImage;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.tree.entity.Tree;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter {

    public Post toPost(String content, Profile profile, List<String> postImageUrls) {
        return Post.builder()
                .content(content)
                .reactionCount(0)
                .postImages(postImageUrls)
                .profile(profile)
                .tree(profile.getTree())
                .build();
    }


    public PostResponseDTO.createPost toCreatePost(Post savedPost) {
        return PostResponseDTO.createPost.builder()
                .postId(savedPost.getId())
                .postImageUrls(savedPost.getPostImages())
                .writerId(savedPost.getProfile().getMember().getId())
                .treeId(savedPost.getTree().getId())
                .build();
    }

    public PostResponseDTO.getFeed toGetFeed(Post post) {
        return PostResponseDTO.getFeed.builder()
                .profileImageUrl(post.getProfile().getProfileImageUrl())
                .memberName(post.getProfile().getMemberName())
                .content(post.getContent())
                .postImageUrls(post.getPostImages())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public PostResponseDTO.getPost toGetPost(Post post) {
        return PostResponseDTO.getPost.builder()
                .postId(post.getId())
                .profileImageUrl(post.getProfile().getProfileImageUrl())
                .memberName(post.getProfile().getMemberName())
                .content(post.getContent())
                .postImageUrls(post.getPostImages())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
