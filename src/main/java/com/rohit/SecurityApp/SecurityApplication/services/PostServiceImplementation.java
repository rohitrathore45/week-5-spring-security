package com.rohit.SecurityApp.SecurityApplication.services;

import com.rohit.SecurityApp.SecurityApplication.dto.PostDto;
import com.rohit.SecurityApp.SecurityApplication.entities.PostEntity;
import com.rohit.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.rohit.SecurityApp.SecurityApplication.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService{

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPost() {
        return postRepository.
                findAll().
                stream().
                map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDto.class);
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found " + postId));
        return modelMapper.map(postEntity, PostDto.class);

    }
}

