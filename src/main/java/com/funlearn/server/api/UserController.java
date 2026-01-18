package com.funlearn.server.api;

import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/")
@SecurityRequirement(name = "ApiKeyAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable UUID id) {
        return userService.getUserByUserId(id);
    }

    @PostMapping
    public void save(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    @PutMapping()
    public void update(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
    }
}
