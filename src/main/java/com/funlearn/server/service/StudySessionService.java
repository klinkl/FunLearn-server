package com.funlearn.server.service;

import com.funlearn.server.api.model.StudySessionDTO;
import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.model.StudySession;
import com.funlearn.server.repository.StudySessionRepository;
import com.funlearn.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class StudySessionService {
    private final StudySessionRepository studySessionRepository;
    private final ModelQuestService modelQuestService;
    private final UserService userService;

    public StudySessionService(StudySessionRepository studySessionRepository, ModelQuestService modelQuestService, UserService userService) {
        this.studySessionRepository = studySessionRepository;
        this.modelQuestService = modelQuestService;
        this.userService = userService;
    }

    public void save(StudySessionDTO studySessionDTO) {
        if(studySessionRepository.existsById(studySessionDTO.getStudySessionId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A Study Session with this id already exists");
        }
        if (!userService.existsByUserId(studySessionDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        StudySession session = convertToStudySession(studySessionDTO);
        studySessionRepository.save(session);

        modelQuestService.updateQuestsForUser(studySessionDTO.getUserId(),session);

        userService.updateWithStudySession(studySessionDTO.getUserId(),session);
    }

    private StudySessionDTO convertToDTO(StudySession session) {
        return new StudySessionDTO(session.getUserId(), session.getCardsLearnt(), session.getXp(), session.getTimeStamp(), session.getStudySessionId());
    }

    private StudySession convertToStudySession(StudySessionDTO session) {
        return new StudySession(session.getStudySessionId(), session.getTimeStamp(), session.getXp(), session.getCardsLearnt(), session.getUserId());
    }
    public List<StudySessionDTO>getAll(){
        return studySessionRepository.findAll().stream().map(this::convertToDTO).toList();
    }
    public StudySessionDTO getById(UUID id) {
        return convertToDTO(studySessionRepository.getByStudySessionId(id));
    }
}
