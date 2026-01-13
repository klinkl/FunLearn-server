package com.funlearn.server.service;

import com.funlearn.server.api.model.StudySessionDTO;
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
    private final UserRepository userRepository;

    public StudySessionService(StudySessionRepository studySessionRepository, UserRepository userRepository) {
        this.studySessionRepository = studySessionRepository;
        this.userRepository = userRepository;
    }

    public void save(StudySessionDTO studySessionDTO) {
        if (!userRepository.existsById(studySessionDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        studySessionRepository.save(this.convertToStudySession(studySessionDTO));
    }

    private StudySessionDTO convertToDTO(StudySession session) {
        return new StudySessionDTO(session.getUserId(), session.getCardsLearnt(), session.getXp(), session.getTimeStamp(), session.getStudySessionId());
    }

    private StudySession convertToStudySession(StudySessionDTO session) {
        return new StudySession(session.getStudySessionId(), session.getTimeStamp(), session.getXp(), session.getCardsLearnt(), session.getUserId());
    }
}
