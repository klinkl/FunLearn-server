package com.funlearn.server.api.model;


import java.time.Instant;
import java.util.UUID;

public class StudySessionDTO {
    private UUID studySessionId;
    private Instant timeStamp;
    private int xp;
    private int cardsLearnt;
    private UUID userId;
}
