package com.coinShiftProject.coinShiftProject.Service.Impl;

import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import com.coinShiftProject.coinShiftProject.Entity.User;
import com.coinShiftProject.coinShiftProject.Repository.UserRepository;
import com.coinShiftProject.coinShiftProject.Service.AuthService;
import com.coinShiftProject.coinShiftProject.Service.EmailService;
import com.coinShiftProject.coinShiftProject.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final int otpTTL = 5;

    public String sendOtp(String email) {

        if (userRepo.findByEmail(email).isPresent()) {
            return "User already exists";
        }

        String existingOtp = redisTemplate.opsForValue().get(email);
        if (existingOtp != null) {
            emailService.sendOtp(email,existingOtp);
            return "Otp send successfully";
        }

        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 1000000)
        );

        redisTemplate.opsForValue()
                .set(email, otp, otpTTL, TimeUnit.MINUTES);

        emailService.sendOtp(email,otp);
        return "Otp send successfully";
    }

    @Override
    public String create(createUserDTO req) {

        String savedOtp=redisTemplate.opsForValue().get(req.getEmail());
        if(savedOtp==null){
            return "OTP expired";
        }
        if(!Objects.equals(req.getOtp(), savedOtp)){
            return "Invalid OTP";
        }

        User user=new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setMobile(req.getPhone());

        userRepo.save(user);
        return "User created Successfully";


    }

    @Override
    public LoginResponse login(String email, String password) {
        User user= (User) userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token =jwtUtil.generateToken(email);

        return new LoginResponse("User Logged in",token);
    }
}
