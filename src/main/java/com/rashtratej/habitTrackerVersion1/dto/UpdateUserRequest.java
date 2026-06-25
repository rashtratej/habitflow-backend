package com.rashtratej.habitTrackerVersion1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {

    @NotBlank
    private String userName;

    @Email
    @NotBlank
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(
            String userName
    ) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email
    ) {
        this.email = email;
    }
}