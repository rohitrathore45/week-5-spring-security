package com.rohit.SecurityApp.SecurityApplication.services;

import com.rohit.SecurityApp.SecurityApplication.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPost();

    PostDto createNewPost(PostDto inputPost);

    PostDto getPostById(Long postId);
}

