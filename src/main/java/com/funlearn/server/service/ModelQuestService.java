package com.funlearn.server.service;

import com.funlearn.server.api.model.ModelQuestDTO;
import com.funlearn.server.model.ModelQuest;
import com.funlearn.server.repository.ModelQuestRepository;
import com.funlearn.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class ModelQuestService {
    private final ModelQuestRepository modelQuestRepository;
    private final UserRepository userRepository;

    public ModelQuestService(ModelQuestRepository modelQuestRepository, UserRepository userRepository) {
        this.modelQuestRepository = modelQuestRepository;
        this.userRepository = userRepository;
    }

    public ModelQuestDTO getModelQuestByQuestId(UUID questId) {
        return modelQuestRepository.findById(questId).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("ModelQuest not found"));
    }

    public List<ModelQuestDTO> getModelQuestsByUserId(UUID id) {
        return modelQuestRepository.getByUserId(id.toString()).stream().map(this::convertToDTO).toList();
    }

    private ModelQuest convertToModelQuest(ModelQuestDTO modelQuest) {
        return new ModelQuest(modelQuest.isFriendsQuest(), modelQuest.isFinished(), modelQuest.getRequestedValue(), modelQuest.getCurrentValue(), modelQuest.getExpiryDate(), modelQuest.getStartDate(), modelQuest.getQuestType(), modelQuest.getUserIds(), modelQuest.getQuestId());

    }

    private ModelQuestDTO convertToDTO(ModelQuest modelQuest) {
        return new ModelQuestDTO(modelQuest.getQuestId(), modelQuest.getUserIds(), modelQuest.getQuestType(), modelQuest.getStartDate(), modelQuest.getExpiryDate(), modelQuest.getCurrentValue(), modelQuest.getRequestedValue(), modelQuest.isFinished(), modelQuest.isFriendsQuest());
    }

    public void save(ModelQuestDTO modelQuest) {
        for (String userId : modelQuest.getUserIds()) {
            UUID Id = UUID.fromString(userId);
            if (!userRepository.existsById(Id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
        modelQuestRepository.save(convertToModelQuest(modelQuest));
    }

    public void update(ModelQuestDTO modelQuest) {
        if (!modelQuestRepository.existsById(modelQuest.getQuestId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelQuest not found");
        }
        modelQuestRepository.save(convertToModelQuest(modelQuest));
    }
}
