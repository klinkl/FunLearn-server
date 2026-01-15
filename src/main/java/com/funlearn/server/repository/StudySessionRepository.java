package com.funlearn.server.repository;

import com.funlearn.server.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudySessionRepository extends JpaRepository<StudySession, UUID> {
    List<StudySession> getByUserId(UUID userId);

    StudySession getByStudySessionId(UUID id);

}
