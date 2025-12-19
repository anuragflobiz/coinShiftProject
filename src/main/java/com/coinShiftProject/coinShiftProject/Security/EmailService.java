package com.coinShiftProject.coinShiftProject.Security;


import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    public void sendOtp(String toEmail,String otp);
}
