package com.funlearn.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.UUID;
@Entity
public class ModelUser {
    @Id
    private UUID userId;
    private String username;
    private int totalXP;
    private int totalCardsLearned;
    private Instant lastStudyDate;
    private int level;
    private int xpToNextLevel;
    private int currentStreak;

    protected ModelUser() {
    }

    public ModelUser(UUID userId,String username, int totalXP, int totalCardsLearned, Instant lastStudyDate, int level, int xpToNextLevel, int currentStreak) {
        this.username = username != null ? username : "User";
        this.userId = userId != null ? userId : UUID.randomUUID();
        this.totalXP = totalXP;
        this.totalCardsLearned = totalCardsLearned;
        this.lastStudyDate = lastStudyDate;
        this.level = level;
        this.xpToNextLevel = xpToNextLevel;
        this.currentStreak = currentStreak;
    }

    public UUID getUserId() {
        return userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public int getTotalCardsLearned() {
        return totalCardsLearned;
    }

    public void setTotalCardsLearned(int totalCardsLearned) {
        this.totalCardsLearned = totalCardsLearned;
    }

    public Instant getLastStudyDate() {
        return lastStudyDate;
    }

    public void setLastStudyDate(Instant lastStudyDate) {
        this.lastStudyDate = lastStudyDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ModelUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", totalXP=" + totalXP +
                ", totalCardsLearned=" + totalCardsLearned +
                ", lastStudyDate=" + lastStudyDate +
                ", level=" + level +
                ", xpToNextLevel=" + xpToNextLevel +
                ", currentStreak=" + currentStreak +
                '}';
    }
}
