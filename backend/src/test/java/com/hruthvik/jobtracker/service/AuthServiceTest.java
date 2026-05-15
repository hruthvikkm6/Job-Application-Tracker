package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.request.RegisterRequest;
import com.hruthvik.jobtracker.dto.response.AuthResponse;
import com.hruthvik.jobtracker.entity.User;
import com.hruthvik.jobtracker.exception.UserAlreadyExistsException;
import com.hruthvik.jobtracker.mapper.UserMapper;
import com.hruthvik.jobtracker.repository.UserRepository;
import com.hruthvik.jobtracker.security.JwtService;
import com.hruthvik.jobtracker.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        registerRequest = RegisterRequest.builder()
                .fullName("Hruthvik K M")
                .email("hruthvik@example.com")
                .password("password123")
                .build();
    }

    @Test
    void register_ShouldReturnAuthResponse_WhenSuccessful() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUserAlreadyExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(User.class));
    }
}
