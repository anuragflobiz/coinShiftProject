package com.coinShiftProject.coinShiftProject.Controller;

import com.coinShiftProject.coinShiftProject.DTO.*;
import com.coinShiftProject.coinShiftProject.Service.AuthService;
import com.coinShiftProject.coinShiftProject.enums.OtpPurpose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/getOtp/{email}/{cause}")
    public String generateOtp(@PathVariable String email,@PathVariable OtpPurpose cause){
        return authService.sendOtp(email,cause);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody createUserDTO user){
        return authService.create(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginReq req){
        return authService.login(req.getEmail(),req.getPassword());
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO req, Authentication authentication) {
        if (!authentication.getName().equals(req.getEmail())) {
            return ResponseEntity.status(403).body("Unauthorized access");
        }

        return ResponseEntity.ok(authService.changePassword(req));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO req) {
        return ResponseEntity.ok(authService.forgotPassword(req));
    }


}
