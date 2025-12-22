package com.coinShiftProject.coinShiftProject.Service;


import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendOtp(String toEmail,String otp);
}
