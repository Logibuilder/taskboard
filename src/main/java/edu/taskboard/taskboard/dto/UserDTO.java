package edu.taskboard.taskboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.taskboard.taskboard.model.Role;
import edu.taskboard.taskboard.model.User;
import jakarta.validation.constraints.*;

public record UserDTO(
        // Fields for both request and response
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be 2-50 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "Role is required")
        Role role,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String color,

        // Write-only for requests (won't appear in responses)
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "Password is required")
        String password,

        // Read-only for responses (won't accept input)

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        boolean active

)

{


    // Static factory method for creating from User entity
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                null,
                user.getName(),
                user.getEmail(),
                user.getRole(),
                null,
                null, // Password never exposed
                user.isActive()
        );
    }

    // Method to convert to User entity
    public User toEntity() {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .role(this.role)
                .color(this.color)
                .password(this.password)
                .build();
    }
}