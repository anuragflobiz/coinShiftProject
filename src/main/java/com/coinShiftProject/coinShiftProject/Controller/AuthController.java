package com.coinShiftProject.coinShiftProject.Controller;

import com.coinShiftProject.coinShiftProject.DTO.LoginReq;
import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import com.coinShiftProject.coinShiftProject.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/getOtp/{email}")
    public String generateOtp(@PathVariable String email){
        return authService.sendOtp(email);
    }

    @PostMapping("/createUser")
    public String createUser(@RequestBody createUserDTO user){
        return authService.create(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginReq req){
        return authService.login(req.getEmail(),req.getPassword());
    }


}
