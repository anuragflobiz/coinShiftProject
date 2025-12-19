package com.coinShiftProject.coinShiftProject.Service.Impl;

import com.coinShiftProject.coinShiftProject.Service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOtp(String toEmail,String otp){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setTo(toEmail);
        msg.setSubject("OTP Verification");
        msg.setText("Your otp for signup in Coin Shift is "+otp+".\n OTP will be expired in next 5 minutes.");

        javaMailSender.send(msg);
    }
}
