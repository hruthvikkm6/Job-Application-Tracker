package com.hruthvik.jobtracker.service.impl;

import com.hruthvik.jobtracker.dto.request.AuthRequest;
import com.hruthvik.jobtracker.dto.request.RegisterRequest;
import com.hruthvik.jobtracker.dto.response.AuthResponse;
import com.hruthvik.jobtracker.dto.response.UserResponse;
import com.hruthvik.jobtracker.entity.Role;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.exception.UnauthorizedAccessException;
import com.hruthvik.jobtracker.exception.UserAlreadyExistsException;
import com.hruthvik.jobtracker.mapper.UserMapper;
import com.hruthvik.jobtracker.repository.UserRepository;
import com.hruthvik.jobtracker.security.JwtService;
import com.hruthvik.jobtracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userMapper.toResponse(savedUser))
                .build();
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(userMapper.toResponse(user))
                .build();
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedAccessException("User not found"));
    }

    @Override
    public UserResponse getMe() {
        return userMapper.toResponse(getCurrentUser());
    }
}
