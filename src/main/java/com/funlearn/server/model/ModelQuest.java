package com.funlearn.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;
@Entity
public class ModelQuest {
    @Id
    private UUID questId;
    private ArrayList<String> userIds;
    private QuestType questType;
    private Instant startDate;
    private Instant expiryDate;
    private int currentValue;
    private int requestedValue;
    private boolean finished;
    private boolean friendsQuest;
    protected ModelQuest() {}
    public ModelQuest(boolean friendsQuest, boolean finished, int requestedValue, int currentValue, Instant expiryDate, Instant startDate, QuestType questType, ArrayList<String> userIds, UUID questId) {
        this.friendsQuest = friendsQuest;
        this.finished = finished;
        this.requestedValue = requestedValue;
        this.currentValue = currentValue;
        this.expiryDate = expiryDate;
        this.startDate = startDate;
        this.questType = questType;
        this.userIds = userIds;
        this.questId = questId != null ? questId : UUID.randomUUID();;
    }


    public UUID getQuestId() {
        return questId;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public void setQuestType(QuestType questType) {
        this.questType = questType;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public int getRequestedValue() {
        return requestedValue;
    }

    public void setRequestedValue(int requestedValue) {
        this.requestedValue = requestedValue;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFriendsQuest() {
        return friendsQuest;
    }

    public void setQuestId(UUID questId) {
        this.questId = questId;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public void setFriendsQuest(boolean friendsQuest) {
        this.friendsQuest = friendsQuest;
    }
}

