package org.example.tree.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostService {
    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
    private final PostConverter postConverter;
    private final ProfileService profileService;

    @Transactional
    public PostResponseDTO.createPost createPost(Long treeId, PostRequestDTO.createPost request, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postConverter.toPost(request.getContent(), profile, request.getPostImageUrls());
        Post savedPost = postCommandService.createPost(post);
        return postConverter.toCreatePost(savedPost);
    }

    @Transactional
    public List<PostResponseDTO.getFeed> getFeed(Long treeId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        List<Post> posts = postQueryService.getPosts(profile.getTree());
        return posts.stream()
                .map(post -> postConverter.toGetFeed(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDTO.getPost getPost(Long treeId, Long postId, String token) {
        Profile profile = profileService.getTreeProfile(token, treeId);
        Post post = postQueryService.findById(postId);
        return postConverter.toGetPost(post);
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
