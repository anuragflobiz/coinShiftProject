package com.coinShiftProject.coinShiftProject.Controller;

import com.coinShiftProject.coinShiftProject.DTO.LoginReq;
import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.OtpRequest;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import com.coinShiftProject.coinShiftProject.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/otp")
    public String sendOtp(@RequestBody OtpRequest req) {
        return authService.sendOtp(req.getEmail(), req.getPurpose());
    }

    @PostMapping("/users")
    public String signup(@RequestBody createUserDTO dto) {
        return authService.create(dto);
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginReq req) {
        return authService.login(req.getEmail(), req.getPassword());
    }

    @PostMapping("/auth/logout")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        authService.logout(token);
        return "Logged out successfully";
    }
}
