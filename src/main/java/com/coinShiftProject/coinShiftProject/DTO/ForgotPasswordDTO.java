package com.coinShiftProject.coinShiftProject.DTO;

import lombok.Data;

@Data
public class ForgotPasswordDTO {
    private String email;
    private String newPassword;
    private String otp;
}
