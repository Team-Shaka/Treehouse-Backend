package org.example.tree.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tree.domain.branch.service.BranchService;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.member.service.MemberQueryService;
import org.example.tree.domain.post.converter.PostConverter;
import org.example.tree.domain.post.dto.PostRequestDTO;
import org.example.tree.domain.post.dto.PostResponseDTO;
import org.example.tree.domain.post.entity.Post;
import org.example.tree.domain.post.entity.PostImage;
import org.example.tree.domain.profile.entity.Profile;
import org.example.tree.domain.profile.service.ProfileQueryService;
import org.example.tree.domain.profile.service.ProfileService;
import org.example.tree.domain.reaction.dto.ReactionResponseDTO;
import org.example.tree.domain.reaction.service.ReactionService;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostService {
    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
    private final PostConverter postConverter;
    private final ProfileService profileService;
    private final ReactionService reactionService;
    private final PostImageCommandService postImageCommandService;
    private final BranchService branchService;

    @Transactional
<<<<<<< Updated upstream
    public PostResponseDTO.createPost createPost(Long treeId, PostRequestDTO.createPost request, String token) throws Exception {
        List<MultipartFile> images = request.getImages() != null && !request.getImages().isEmpty() ? request.getImages() : new ArrayList<>();
=======
    public PostResponseDTO.createPost createPost(Long treeId, PostRequestDTO.createPost request, List<MultipartFile> images, String token) throws Exception {
>>>>>>> Stashed changes
        Profile profile = profileService.getTreeProfile(token, treeId);
        List<PostImage> postImages = postConverter.toPostImages(images);
        Post post = postConverter.toPost(request.getContent(), profile);
        Post savedPost = postCommandService.createPost(post);
        for (PostImage postImage : postImages) {
            postImage.setPost(savedPost); // 각 PostImage에 Post 설정
            postImageCommandService.createPostImage(postImage);// PostImage 저장
            savedPost.addPostImage(postImage); // Post에 PostImage 추가
        }
        return postConverter.toCreatePost(savedPost);
    }

    @Transactional
    public List<PostResponseDTO.getFeed> getFeed(Long treeId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        List<Post> posts = postQueryService.getPosts(profile.getTree());
        return posts.stream()
                .map(post -> {
                    // 작성자와의 branch degree를 가져옵니다.
                    int branchDegree = branchService.calculateBranchDegree(treeId, profile.getId(), post.getProfile().getId());
                    // 각 포스트에 대한 반응들을 가져옵니다.
                    List<ReactionResponseDTO.getReaction> reactions = reactionService.getPostReactions(treeId, post.getId(), token);
                    // Post와 해당 Post의 반응들을 포함하여 DTO를 생성합니다.
                    return postConverter.toGetFeed(post, branchDegree, reactions);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDTO.getPost getPost(Long treeId, Long postId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        int branchDegree = branchService.calculateBranchDegree(treeId, profile.getId(), post.getProfile().getId());
        List<ReactionResponseDTO.getReaction> reactions = reactionService.getPostReactions(treeId, postId, token);
        return postConverter.toGetPost(post, branchDegree, reactions);
    }

    @Transactional
    public List<PostResponseDTO.getPost> getTreePosts(Long treeId, Long profileId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        List<Post> posts = postQueryService.findByProfileId(profileId);
        return posts.stream()
                .map(post -> {
                    int branchDegree = branchService.calculateBranchDegree(treeId, profile.getId(), post.getProfile().getId());
                    List<ReactionResponseDTO.getReaction> reactions = reactionService.getPostReactions(treeId, post.getId(), token);
                    return postConverter.toGetPost(post, branchDegree, reactions);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(Long treeId, Long postId, PostRequestDTO.updatePost request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        if (!post.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        post.updatePost(request.getContent());
    }

    @Transactional
    public void deletePost(Long treeId, Long postId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        if (!post.getProfile().getId().equals(profile.getId())) {
            throw new GeneralException(GlobalErrorCode.AUTHENTICATION_REQUIRED);
        }
        postCommandService.deletePost(post);
    }
}
