package com.rashtratej.habitTrackerVersion1.dto;

public class UserResponse {

    private Long id;
    private String userName;
    private String email;
    private String profilePictureUrl;

    public UserResponse(
            Long id,
            String userName,
            String email,
            String profilePictureUrl
    ) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
}