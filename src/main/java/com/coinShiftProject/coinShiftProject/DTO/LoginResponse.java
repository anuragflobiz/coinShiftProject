package com.coinShiftProject.coinShiftProject.DTO;


import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
}

