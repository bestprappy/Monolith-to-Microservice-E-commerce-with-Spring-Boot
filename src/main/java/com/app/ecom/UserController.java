package com.app.ecom;

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

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.fetchAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user){
        boolean updated = userService.updatedUser(id, user);
        if(updated) return new ResponseEntity<>("User updated!", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id
    ){
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
