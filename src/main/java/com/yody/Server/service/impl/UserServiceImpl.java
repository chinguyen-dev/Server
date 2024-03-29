package com.yody.Server.service.impl;

//import com.yody.Server.config.JwtComponent;

import com.yody.Server.components.MapperComponent;
import com.yody.Server.config.JwtService;
import com.yody.Server.dto.AuthenticationRequest;
import com.yody.Server.dto.AuthenticationResponse;
import com.yody.Server.dto.user.UserDTO;
import com.yody.Server.dto.user.UserRegisterRequest;
import com.yody.Server.entities.Role;
import com.yody.Server.entities.User;
import com.yody.Server.service.impl.exception.InvalidException;
import com.yody.Server.service.impl.exception.NotFondException;
import com.yody.Server.repositories.RoleRepository;
import com.yody.Server.repositories.UserRepository;
import com.yody.Server.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    UserDetailsService userDetailsService;
    private final MapperComponent mapperComponent;

    @Override
    public List<UserDTO> getUsers() {
        return this.userRepository.findAll().stream().map(mapperComponent::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFondException("user doesn't exist"));
        return mapperComponent.toDto(user);
    }


    @Override
    public UserDTO addRoleToUser(String email, String roleName) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new NotFondException("user does not exits"));
        Role role = this.roleRepository.findByName(roleName);
        user.getRoles().add(role);
        return this.mapperComponent.toDto(this.userRepository.save(user));
    }

    @Override
    public UserDTO saveUser(UserRegisterRequest userRegisterRequest) {
        User user = User.builder()
                .fullName(userRegisterRequest.getFullName())
                .email(userRegisterRequest.getEmail())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .roles(new HashSet<>())
                .build();
        Role role = roleRepository.findByName("USER");
        user.addRole(role);
        User userInserted = this.userRepository.save(user);
        return this.mapperComponent.toDto(userInserted);
    }

    @Override
    public AuthenticationResponse authentication(AuthenticationRequest authenticationRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
            User user = this.userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new NotFondException("not found"));
            List<String> authorities = user
                    .getAuthorities()
                    .stream()
                    .map(grantedAuthority -> grantedAuthority.getAuthority())
                    .collect(Collectors.toList());
            ;

            return AuthenticationResponse
                    .builder()
                    .token(jwtService.generateToken(user))
                    .type("bearer")
                    .principle(user.getEmail())
                    .username(user.getFullName())
                    .roles(authorities)
                    .build();

        } catch (Exception e) {
            throw new InvalidException("Invalid User");
        }
    }

    @Override
    public boolean validateEmail(String email) {
        User user = this.userRepository.findByEmail(email).orElse(null);
        if (user != null) return false;
        return true;
    }


}
