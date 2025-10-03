package com.rohit.SecurityApp.SecurityApplication.services;

import com.rohit.SecurityApp.SecurityApplication.dto.LoginDto;
import com.rohit.SecurityApp.SecurityApplication.dto.SignUpDto;
import com.rohit.SecurityApp.SecurityApplication.dto.UserDto;
import com.rohit.SecurityApp.SecurityApplication.entities.UserEntity;
import com.rohit.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import com.rohit.SecurityApp.SecurityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + username + " not found."));
    }

    public UserEntity getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    public UserDto singUp(SignUpDto signUpDto) {
        Optional<UserEntity> user = userRepository.findByEmail(signUpDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exist : " + signUpDto.getEmail());
        }

        UserEntity toCreate = modelMapper.map(signUpDto, UserEntity.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));

        UserEntity savedUser = userRepository.save(toCreate);

        return modelMapper.map(savedUser, UserDto.class);
    }

}
