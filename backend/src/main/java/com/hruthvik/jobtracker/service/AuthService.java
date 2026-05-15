package com.hruthvik.jobtracker.service;

import com.hruthvik.jobtracker.dto.request.AuthRequest;
import com.hruthvik.jobtracker.dto.request.RegisterRequest;
import com.hruthvik.jobtracker.dto.response.AuthResponse;
import com.hruthvik.jobtracker.dto.response.UserResponse;
import com.hruthvik.jobtracker.entity.User;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse authenticate(AuthRequest request);
    User getCurrentUser();
    UserResponse getMe();
}
