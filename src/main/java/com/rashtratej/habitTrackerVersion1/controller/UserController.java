package com.rashtratej.habitTrackerVersion1.controller;
import com.rashtratej.habitTrackerVersion1.dto.ChangePasswordRequest;
import com.rashtratej.habitTrackerVersion1.dto.UpdateUserRequest;
import com.rashtratej.habitTrackerVersion1.dto.UserResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.rashtratej.habitTrackerVersion1.entity.User;
import com.rashtratej.habitTrackerVersion1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Tag(
        name = "User Management",
        description = "Registration, login and account management"
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get current user profile",
            description = "Returns currently authenticated user details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User retrieved successfully"
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse user =
                userService.getCurrentUser();

        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Update user profile",
            description = "Updates current authenticated user profile"
    )
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

    @Operation(
            summary = "Change password",
            description = "Changes password for authenticated user"
    )
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


    @Operation(
            summary = "Delete user account",
            description = "Deletes currently authenticated user account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "User deleted successfully"
    )
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