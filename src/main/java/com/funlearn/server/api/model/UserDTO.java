package com.funlearn.server.api.model;

import jakarta.persistence.Id;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID userId;
    private String username;
    private int totalXP;
    private int totalCardsLearned;
    private Instant lastStudyDate;
    private int level;
    private int xpToNextLevel;
    private int currentStreak;


    private List<UUID> friends;

    public UserDTO(UUID userId, String username, int totalXP, int totalCardsLearned, Instant lastStudyDate, int level, int xpToNextLevel, int currentStreak, List<UUID> friends) {
        this.userId = userId;
        this.username = username;
        this.totalXP = totalXP;
        this.totalCardsLearned = totalCardsLearned;
        this.lastStudyDate = lastStudyDate;
        this.level = level;
        this.xpToNextLevel = xpToNextLevel;
        this.currentStreak = currentStreak;
        this.friends = friends;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public List<UUID> getFriends() {
        return friends;
    }

    public void setFriends(List<UUID> friends) {
        this.friends = friends;
    }
}