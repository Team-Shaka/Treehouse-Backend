package org.example.tree.domain.post.converter;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.post.dto.PostRequestDTO;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.entity.PostImage;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.global.common.amazons3.S3UploadService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostConverter {
    private final S3UploadService s3UploadService;

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
                .postImageUrls(imageUrls)
                .authorId(savedPost.getProfile().getMember().getId())
                .treeId(savedPost.getTree().getId())
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
                .createdAt(post.getCreatedAt())
                .build();
    }

    public List<PostImage> toPostImages(List<MultipartFile> images) throws Exception {
        List<PostImage> postImages = new ArrayList<>();
        for(MultipartFile image :images) {
            if (!image.isEmpty()) {
                String postImageUrl = s3UploadService.uploadImage(image); // 이미지 업로드 후 URL 획득
                PostImage postImage = PostImage.builder()
                        .imageUrl(postImageUrl) // PostImage 엔티티에는 imageUrl 필드가 있어야 합니다.
                        .build();
                postImages.add(postImage);
            }
        }
        return postImages;
    }
}
