package com.coinShiftProject.coinShiftProject.Security;

import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    public String sendOtp(String email);

    public String create(createUserDTO user);

    public LoginResponse login(String email, String password);
}
