package com.rashtratej.habitTrackerVersion1.controller;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
        public String registerUser(@RequestParam("username") String username,
                                   @RequestParam("avatar") MultipartFile profilepicture) throws IOException {
            User profile = new User();
            profile.setUserName(username);


            return "User profile and image binary saved to DB!";
        }
    }

