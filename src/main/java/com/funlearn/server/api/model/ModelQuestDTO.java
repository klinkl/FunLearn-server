package com.funlearn.server.api.model;

import com.funlearn.server.model.QuestType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

public class ModelQuestDTO {
    private UUID questId;
    private ArrayList<String> userIds;
    private QuestType questType;
    private Instant startDate;
    private Instant expiryDate;
    private int currentValue;
    private int requestedValue;
    private boolean finished;
    private boolean friendsQuest;
}