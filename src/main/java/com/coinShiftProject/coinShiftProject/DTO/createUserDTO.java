package com.coinShiftProject.coinShiftProject.DTO;


import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class createUserDTO {

    private String name;
    private String email;
    private String otp;
    private String phone;
    private String password;

}

