package com.crud.api.controller;


import com.crud.api.dto.CreateUser;
import com.crud.api.dto.UpdateUser;
import com.crud.api.dto.ViewUser;
import com.crud.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ViewUser createUser(@RequestBody CreateUser createUser) {
        return userService.create(createUser);
    }

//    @PostMapping()
//    public ResponseEntity<ViewUser> createUser2(@RequestBody CreateUser createUser) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUser));
//    }

    @GetMapping()
    public List<ViewUser> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewUser> getById(@PathVariable long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViewUser> updateUser(@PathVariable long id, @RequestBody UpdateUser updateUser) {
        return userService.updateUser(id, updateUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteById(id);
    }

}
