package com.funlearn.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;
@Entity
public class StudySession {
    @Id
    private UUID studySessionId;
    private Instant timeStamp;
    private int xp;
    private int cardsLearnt;
    private UUID userId;

    protected StudySession() {

    }
    public StudySession(UUID studySessionId, Instant timeStamp, int xp, int cardsLearnt, UUID userId) {
        this.studySessionId = studySessionId;
        this.timeStamp = timeStamp;
        this.xp = xp;
        this.cardsLearnt = cardsLearnt;
        this.userId = userId;
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

    public void setStudySessionId(UUID studySessionId) {
        this.studySessionId = studySessionId;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setCardsLearnt(int cardsLearnt) {
        this.cardsLearnt = cardsLearnt;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
