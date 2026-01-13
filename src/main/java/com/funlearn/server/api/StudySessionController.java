package com.funlearn.server.api;

import com.funlearn.server.api.model.StudySessionDTO;
import com.funlearn.server.model.StudySession;
import com.funlearn.server.service.StudySessionService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/studysessions/")
public class StudySessionController {
    private final StudySessionService studySessionService;

    public StudySessionController(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @PostMapping
    public void saveStudySession(@RequestBody StudySessionDTO session) {
        studySessionService.save(session);
    }

}
