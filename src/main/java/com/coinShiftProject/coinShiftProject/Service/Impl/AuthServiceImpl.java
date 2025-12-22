package com.coinShiftProject.coinShiftProject.Service.Impl;

import com.coinShiftProject.coinShiftProject.DTO.ChangePasswordDTO;
import com.coinShiftProject.coinShiftProject.DTO.ForgotPasswordDTO;
import com.coinShiftProject.coinShiftProject.DTO.LoginResponse;
import com.coinShiftProject.coinShiftProject.DTO.createUserDTO;
import com.coinShiftProject.coinShiftProject.Entity.User;
import com.coinShiftProject.coinShiftProject.Repository.UserRepository;
import com.coinShiftProject.coinShiftProject.Service.AuthService;
import com.coinShiftProject.coinShiftProject.Service.EmailService;
import com.coinShiftProject.coinShiftProject.Security.JwtUtil;
import com.coinShiftProject.coinShiftProject.enums.OtpPurpose;
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

    public String sendOtp(String email, OtpPurpose cause) {

        if (cause == OtpPurpose.SIGNUP &&
                userRepo.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        if (cause == OtpPurpose.FORGOT_PASSWORD &&
                userRepo.findByEmail(email).isEmpty()) {
            throw new RuntimeException("User not found");
        }

        String key = cause + ":" + email;

        String existingOtp = redisTemplate.opsForValue().get(key);
        if (existingOtp != null) {
            redisTemplate.expire(key, otpTTL, TimeUnit.MINUTES);
            emailService.sendOtp(email, existingOtp);
            return "Otp sent successfully";
        }

        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 1000000)
        );

        redisTemplate.opsForValue()
                .set(key, otp, otpTTL, TimeUnit.MINUTES);

        emailService.sendOtp(email, otp);
        return "Otp sent successfully";
    }

    @Override
    public String create(createUserDTO req) {
        String key = OtpPurpose.SIGNUP + ":" + req.getEmail();
        String savedOtp = redisTemplate.opsForValue().get(key);
        if (savedOtp == null) {
            return "OTP expired";
        }
        if (!Objects.equals(req.getOtp(), savedOtp)) {
            return "Invalid OTP";
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setName(req.getName());
        user.setMobile(req.getPhone());

        userRepo.save(user);
        redisTemplate.delete(key);
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

    @Override
    public String changePassword(ChangePasswordDTO req) {

        User user =(User) userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Enter correct old password");
        }

        if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password cannot be same as old password");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepo.save(user);
        return "Password Updated";
    }

    public String forgotPassword(ForgotPasswordDTO req){

        User user =(User) userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));


        String key = OtpPurpose.FORGOT_PASSWORD +":" + req.getEmail();
        String savedOtp = redisTemplate.opsForValue().get(key);

        if (savedOtp == null || !savedOtp.equals(req.getOtp())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        if (passwordEncoder.matches(req.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password cannot be same as old password");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepo.save(user);
        redisTemplate.delete(key);
        return "Password Updated";
    }
}
