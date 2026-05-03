package com.prod.services;


import com.prod.dto.UserDto;
import com.prod.entities.User;
import com.prod.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
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
        return userRepository.findByEmail(username).orElseThrow();
    }

    public UserDto signUp(UserDto userDto){
        Optional<User> user= userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exixts"+ userDto.getEmail());
        }

        User toCreate= modelMapper.map(userDto, User.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User saveUser= userRepository.save(toCreate);
        return userDto;



    }
}
