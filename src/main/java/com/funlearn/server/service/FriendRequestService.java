package com.funlearn.server.service;

import com.funlearn.server.api.model.FriendRequestDTO;
import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.model.FriendRequest;
import com.funlearn.server.model.FriendRequestId;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.repository.FriendRequestRepository;
import io.swagger.models.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;
    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserService userService) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
    }
    private FriendRequestDTO convertToDTO(FriendRequest friendRequest) {
        return new FriendRequestDTO(friendRequest.getFromUser(),friendRequest.getToUser(),friendRequest.isAccepted());
    }
    private FriendRequest convertToEntity(FriendRequestDTO friendRequestDTO) {
        return new FriendRequest(friendRequestDTO.getFromUser(),friendRequestDTO.getToUser(),friendRequestDTO.isAccepted());
    }
    public void save(FriendRequestDTO friendRequestDTO) {
        if (friendRequestRepository.existsById(new FriendRequestId(friendRequestDTO.getFromUser(),friendRequestDTO.getToUser()))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A friend request with this id already exists");
        }
        if (!userService.existsByUserId(friendRequestDTO.getFromUser()) || !userService.existsByUserId(friendRequestDTO.getToUser())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        friendRequestRepository.save(convertToEntity(friendRequestDTO));

        if(friendRequestDTO.isAccepted()){
            addFriends(friendRequestDTO);
        }

    }
    public void update(FriendRequestDTO friendRequestDTO) {
        if (!userService.existsByUserId(friendRequestDTO.getFromUser()) || !userService.existsByUserId(friendRequestDTO.getToUser())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if(!friendRequestDTO.isAccepted()){
            friendRequestRepository.save(convertToEntity(friendRequestDTO));
        }
        else {
            addFriends(friendRequestDTO);
        }

    }
    public void addFriends(FriendRequestDTO friendRequestDTO) {
        UserDTO userA = userService.getUserByUserId(friendRequestDTO.getFromUser());
        UserDTO userB = userService.getUserByUserId(friendRequestDTO.getToUser());
        List<UUID> friendsA = userA.getFriends();
        if (!friendsA.contains(friendRequestDTO.getToUser())) {
            friendsA.add(friendRequestDTO.getToUser());
        }
        List<UUID> friendsB = userB.getFriends();
        if (!friendsB.contains(friendRequestDTO.getFromUser())) {
            friendsB.add(friendRequestDTO.getFromUser());
        }
        userA.setFriends(friendsA);
        userB.setFriends(friendsB);
        userService.update(userA);
        userService.update(userB);
        friendRequestRepository.deleteById(new FriendRequestId(friendRequestDTO.getFromUser(),friendRequestDTO.getToUser()));
    }
    public void delete(FriendRequestDTO friendRequestDTO) {
        friendRequestRepository.delete(convertToEntity(friendRequestDTO));
    }
    public List<FriendRequestDTO> getSent(UUID fromUser) {
        return friendRequestRepository.findByFromUser(fromUser).stream().map(this::convertToDTO).toList();
    }
    public List<FriendRequestDTO> getReceived(UUID toUser) {
        return friendRequestRepository.findByToUser(toUser).stream().map(this::convertToDTO).toList();
    }
}
