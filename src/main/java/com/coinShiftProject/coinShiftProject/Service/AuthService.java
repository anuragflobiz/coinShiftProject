package com.coinShiftProject.coinShiftProject.Service;

import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import com.coinShiftProject.coinShiftProject.enums.OtpPurpose;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    String sendOtp(String email, OtpPurpose purpose);

    String create(createUserDTO user);

    LoginResponse login(String email, String password);

    void logout(String token);
}

