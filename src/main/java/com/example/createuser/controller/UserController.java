package com.example.createuser.controller;

import com.example.createuser.entity.UserLogin;
import com.example.createuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getalluser")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserLogin> users = userService.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(Map.of("success", true, "message", "User not found", "status",
                        "NOTFOUND", "statusCode", 404), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(Map.of("success", true, "status", "SUCCESS", "message",
                        "User list", "statusCode", 200, "data", users),
                        HttpStatus.OK);

            }
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("success", false, "status", "message", e.getMessage(),
                    "INTERNAL_SERVER_ERROR", "statusCode", 501), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
