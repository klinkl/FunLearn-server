package com.funlearn.server.api.model;


import java.time.Instant;
import java.util.UUID;

public class StudySessionDTO {
    private UUID studySessionId;
    private Instant timeStamp;
    private int xp;
    private int cardsLearnt;
    private UUID userId;

    public StudySessionDTO(UUID userId, int cardsLearnt, int xp, Instant timeStamp, UUID studySessionId) {
        this.userId = userId;
        this.cardsLearnt = cardsLearnt;
        this.xp = xp;
        this.timeStamp = timeStamp;
        this.studySessionId = studySessionId;
    }

    public UUID getStudySessionId() {
        return studySessionId;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public int getXp() {
        return xp;
    }

    public int getCardsLearnt() {
        return cardsLearnt;
    }

    public UUID getUserId() {
        return userId;
    }
}
