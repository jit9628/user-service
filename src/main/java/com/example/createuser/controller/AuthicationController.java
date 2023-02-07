package com.example.createuser.controller;

import com.example.createuser.entity.UserLogin;
import com.example.createuser.service.UserService;
import com.example.createuser.utility.JwtUtility;
import com.example.createuser.utility.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthicationController {

 @Autowired
    private UserService userService;
@PostMapping ("/register")
 public ResponseEntity<?>register(@RequestBody UserLogin userLogin)
 {
     try {
         UserLogin exitingUser = userService.findByUserName(userLogin.getUsername());
         if(exitingUser!=null)
         {
             return new ResponseEntity<>(Map.of("success", false, "status", "message", "User already register",
                     "CONFLICT", "statusCode", 409), HttpStatus.CONFLICT);
         }
         else
         {
             userService.register(userLogin);
             return new ResponseEntity<>(Map.of("success", true, "status", "CREATED", "message",
                     "User has been register successfully", "statusCode", 201),
                     HttpStatus.CREATED);

         }
     }
     catch (Exception e)
     {
         return new ResponseEntity<>(Map.of("success", false, "status", "message",e.getMessage(),
                 "INTERNAL_SERVER_ERROR", "statusCode", 501), HttpStatus.INTERNAL_SERVER_ERROR);
     }



 }
    @PostMapping ("/login")
    public ResponseEntity<?>login(@RequestBody UserLogin userLogin)
    {
        try {
            UserLogin exitingUser = userService.findByUserName(userLogin.getUsername());
            if(exitingUser!=null)
            {
                if (PasswordEncoder.isMatchPassword(userLogin.getPassword(), exitingUser.getPassword())) {
                    String token = JwtUtility.createToken(exitingUser);

                    return new ResponseEntity<>(Map.of("success", true, "status", "OK", "statusCode", 200, "message",
                            "User has been login successfully", "token", token), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(Map.of("success", false, "status", "FORBIDDEN", "statusCode", 401,
                            "message", "Password does not match!!"), HttpStatus.FORBIDDEN);
                }
            }
            else
            {
                return new ResponseEntity<>(Map.of("success", false, "status", "NOT_FOUND", "statusCode", 404,
                        "message", "User Not Found!!"), HttpStatus.NOT_FOUND);

            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("success", false, "status", "message",e.getMessage(),
                    "INTERNAL_SERVER_ERROR", "statusCode", 501), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }




}
