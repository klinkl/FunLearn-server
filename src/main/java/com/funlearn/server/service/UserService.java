package com.funlearn.server.service;

import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByUserId(UUID userId) {
        return userRepository.findById(userId).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void save(UserDTO userDTO) {
        userRepository.save(this.convertToUser(userDTO));
    }

    private UserDTO convertToDTO(ModelUser user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getTotalXP(), user.getTotalCardsLearned(), user.getLastStudyDate(), user.getLevel(), user.getXpToNextLevel(), user.getCurrentStreak());
    }

    private ModelUser convertToUser(UserDTO user) {
        return new ModelUser(user.getUserId(), user.getUsername(), user.getTotalXP(), user.getTotalCardsLearned(), user.getLastStudyDate(), user.getLevel(), user.getXpToNextLevel(), user.getCurrentStreak());
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public void update(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.save(this.convertToUser(userDTO));

    }
}
