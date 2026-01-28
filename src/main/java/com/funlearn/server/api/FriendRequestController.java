package com.funlearn.server.api;

import com.funlearn.server.api.model.FriendRequestDTO;
import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.service.FriendRequestService;
import com.funlearn.server.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/friend/")
@SecurityRequirement(name = "ApiKeyAuth")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping
    public void save(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendRequestService.save(friendRequestDTO);
    }
    @PutMapping
    public void accept(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendRequestService.update(friendRequestDTO);
    }
    @DeleteMapping
    public void delete(@RequestBody FriendRequestDTO friendRequestDTO) {
        friendRequestService.delete(friendRequestDTO);
    }
    @GetMapping("/sent/{uuid}")
    public List<FriendRequestDTO> getSent(@PathVariable UUID uuid) {
        return  friendRequestService.getSent(uuid);
    }
    @GetMapping("/received/{uuid}")
    public List<FriendRequestDTO> getReceived(@PathVariable UUID uuid) {
        return  friendRequestService.getReceived(uuid);
    }
}
