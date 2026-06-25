package com.rashtratej.habitTrackerVersion1.controller;
import com.rashtratej.habitTrackerVersion1.dto.ChangePasswordRequest;
import com.rashtratej.habitTrackerVersion1.dto.UpdateUserRequest;
import com.rashtratej.habitTrackerVersion1.dto.UserResponse;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse user =
                userService.getCurrentUser();

        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse>
    updateCurrentUser(@Valid @RequestBody UpdateUserRequest request) {

        UserResponse user =
                userService
                        .updateCurrentUser(
                                request
                        );

        return ResponseEntity.ok(
                user
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<String>
    changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        String response =
                userService.changePassword(
                        request
                );

        return ResponseEntity.ok(
                response
        );
    }

    @DeleteMapping("/me")
    public ResponseEntity<String>
    deleteCurrentUser() {

        String response =
                userService
                        .deleteCurrentUser();

        return ResponseEntity.ok(
                response
        );
    }





}

//@PostMapping("/register")
//public String registerUser(@RequestParam("username") String username,
//                           @RequestParam("avatar") MultipartFile profilepicture) throws IOException {
//    User profile = new User();
//    profile.setUserName(username);
//
//
//    return "User profile and image binary saved to DB!";
//}