package edu.taskboard.taskboard.controller;



import edu.taskboard.taskboard.dto.ApiResponse;
import edu.taskboard.taskboard.dto.UserDTO;
import edu.taskboard.taskboard.exception.business.CodeFormatException;
import edu.taskboard.taskboard.exception.business.DuplicateResourceException;
import edu.taskboard.taskboard.exception.business.EmptyPasswordException;
import edu.taskboard.taskboard.model.User;
import edu.taskboard.taskboard.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping("/hello")
    public String Hello() {
        System.out.println("Sign up");
        return "hello";
    }



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAll() {

        ApiResponse<List<UserDTO>> response = new ApiResponse<>(200, "Users retrieved successfully", userService.getAllUsers());

        return ResponseEntity.ok(response);
    }




}