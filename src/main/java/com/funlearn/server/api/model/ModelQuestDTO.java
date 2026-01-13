package com.funlearn.server.api.model;

import com.funlearn.server.model.QuestType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelQuestDTO {
    private UUID questId;
    private List<String> userIds;
    private QuestType questType;
    private Instant startDate;
    private Instant expiryDate;
    private int currentValue;
    private int requestedValue;
    private boolean finished;
    private boolean friendsQuest;

    public ModelQuestDTO(UUID questId, List<String> userIds, QuestType questType, Instant startDate, Instant expiryDate, int currentValue, int requestedValue, boolean finished, boolean friendsQuest) {
        this.questId = questId;
        this.userIds = userIds;
        this.questType = questType;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.currentValue = currentValue;
        this.requestedValue = requestedValue;
        this.finished = finished;
        this.friendsQuest = friendsQuest;
    }

    public UUID getQuestId() {
        return questId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public QuestType getQuestType() {
        return questType;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int getRequestedValue() {
        return requestedValue;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isFriendsQuest() {
        return friendsQuest;
    }
}