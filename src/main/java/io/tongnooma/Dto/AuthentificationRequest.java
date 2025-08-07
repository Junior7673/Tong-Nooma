package io.tongnooma.Dto;

import lombok.Data;

@Data
public class AuthentificationRequest {
    private String email;
    private String password;
}
