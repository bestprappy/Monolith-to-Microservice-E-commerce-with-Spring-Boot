package com.app.ecom.controller;

import com.app.ecom.dto.user.UserRequest;
import com.app.ecom.dto.user.UserResponse;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest){
        boolean updated = userService.updatedUser(id, userRequest);
        if(updated) return new ResponseEntity<>("User updated!", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ){
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
