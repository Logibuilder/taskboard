package edu.taskboard.taskboard.dto;

import edu.taskboard.taskboard.model.User;
import lombok.Data;


@Data
public class LoginResponseDto {

    private UserDTO user;


    private String accessToken;

    private String refreshToken;
}
