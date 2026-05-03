package com.prod.services;

import com.prod.dto.LoginDto;
import com.prod.dto.UserDto;
import com.prod.entities.User;
import com.prod.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger log= LoggerFactory.getLogger(AuthService.class);

    public UserDto signUp(UserDto userDto){
        Optional<User> user= userRepository.findByEmail(userDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exixts"+ userDto.getEmail());
        }

        User toCreate= modelMapper.map(userDto, User.class);
        log.debug("Going create user in db as {} from {} ",toCreate,userDto);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        User saveUser= userRepository.save(toCreate);
        return modelMapper.map(saveUser, UserDto.class);



    }

    public String login(LoginDto login) {
        log.debug("login password : "+login.getPassword());
        log.debug("for login user encoded password is : "+passwordEncoder.encode(login.getPassword()));
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword())
        );

        User user= (User)authentication.getPrincipal();
        return jwtService.generateJWT(user);

    }
}
