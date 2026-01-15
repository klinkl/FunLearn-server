package com.funlearn.server.service;

import com.funlearn.server.api.model.ModelQuestDTO;
import com.funlearn.server.model.ModelQuest;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.model.QuestType;
import com.funlearn.server.model.StudySession;
import com.funlearn.server.repository.ModelQuestRepository;
import com.funlearn.server.repository.UserRepository;
import org.apache.catalina.User;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelQuestService {
    private final ModelQuestRepository modelQuestRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ModelQuestService(ModelQuestRepository modelQuestRepository, UserRepository userRepository, UserService userService) {
        this.modelQuestRepository = modelQuestRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ModelQuestDTO getModelQuestByQuestId(UUID questId) {
        return modelQuestRepository.findById(questId).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("ModelQuest not found"));
    }

    public List<ModelQuestDTO> getModelQuestsByUserId(UUID id) {
        return modelQuestRepository.getByUserId(id.toString()).stream().map(this::convertToDTO).toList();
    }

    private ModelQuest convertToModelQuest(ModelQuestDTO modelQuest) {
        return new ModelQuest(modelQuest.isFriendsQuest(), modelQuest.isFinished(), modelQuest.getRequestedValue(), modelQuest.getCurrentValue(), modelQuest.getExpiryDate(), modelQuest.getStartDate(), modelQuest.getQuestType(), modelQuest.getUserIds(), modelQuest.getQuestId());

    }

    private ModelQuestDTO convertToDTO(ModelQuest modelQuest) {
        return new ModelQuestDTO(modelQuest.getQuestId(), modelQuest.getUserIds(), modelQuest.getQuestType(), modelQuest.getStartDate(), modelQuest.getExpiryDate(), modelQuest.getCurrentValue(), modelQuest.getRequestedValue(), modelQuest.isFinished(), modelQuest.isFriendsQuest());
    }

    public void save(ModelQuestDTO modelQuest) {
        if (modelQuestRepository.existsById(modelQuest.getQuestId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A Quest with this id already exists");
        }
        for (String userId : modelQuest.getUserIds()) {
            UUID Id;
            try {
                Id = UUID.fromString(userId);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid UUID format for userId:" + userId);
            }
            if (!userRepository.existsById(Id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        }
        modelQuestRepository.save(convertToModelQuest(modelQuest));
    }

    public void update(ModelQuestDTO modelQuest) {
        if (!modelQuestRepository.existsById(modelQuest.getQuestId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ModelQuest not found");
        }
        modelQuestRepository.save(convertToModelQuest(modelQuest));
    }

    public void updateQuestsForUser(UUID userId, StudySession studySession) {
        List<ModelQuestDTO> quests = getModelQuestsByUserId(userId);
        List<ModelQuestDTO> activeQuests = quests.stream()
                .filter(q -> !q.isFinished() && q.getExpiryDate().isAfter(studySession.getTimeStamp()))
                .toList();
        for (ModelQuestDTO quest : activeQuests) {
            int newValue;
            boolean finished;
            switch (quest.getQuestType()) {
                case XP -> {
                    newValue = quest.getCurrentValue() + studySession.getXp();
                    finished = newValue >= quest.getRequestedValue();
                    if (finished) newValue = quest.getRequestedValue();
                    update(new ModelQuestDTO(
                            quest.getQuestId(),
                            quest.getUserIds(),
                            quest.getQuestType(),
                            quest.getStartDate(),
                            quest.getExpiryDate(),
                            newValue,
                            quest.getRequestedValue(),
                            finished,
                            quest.isFriendsQuest()
                    ));
                }
                case CardsLearnt -> {
                    newValue = quest.getCurrentValue() + studySession.getCardsLearnt();
                    finished = newValue >= quest.getRequestedValue();
                    if (finished) newValue = quest.getRequestedValue();
                    update(new ModelQuestDTO(
                            quest.getQuestId(),
                            quest.getUserIds(),
                            quest.getQuestType(),
                            quest.getStartDate(),
                            quest.getExpiryDate(),
                            newValue,
                            quest.getRequestedValue(),
                            finished,
                            quest.isFriendsQuest()
                    ));
                }
                case Streak -> {
                    int newStreak;
                    ModelUser user = userRepository.getUserByUserId(studySession.getUserId());
                    if (user.getLastStudyDate() == null) {
                        newStreak = 1;
                    } else {
                        LocalDate last = LocalDate.ofInstant(user.getLastStudyDate(), ZoneId.systemDefault());
                        LocalDate today = LocalDate.now(ZoneId.systemDefault());

                        long difference = today.toEpochDay() - last.toEpochDay();
                        if (difference == 1) {
                            newStreak = quest.getCurrentValue() + 1;
                        } else if (difference > 1) {
                            newStreak = 1;
                        } else { // difference == 0
                            newStreak = quest.getCurrentValue();
                        }
                    }
                    finished = newStreak >= quest.getRequestedValue();

                    update(new ModelQuestDTO(
                            quest.getQuestId(),
                            quest.getUserIds(),
                            quest.getQuestType(),
                            quest.getStartDate(),
                            quest.getExpiryDate(),
                            newStreak,
                            quest.getRequestedValue(),
                            finished,
                            quest.isFriendsQuest()
                    ));

                }
            }
        }
    }
    @Scheduled(cron = "0 * * * * *", zone = "UTC")
    public void CreateQuests() {
        int length = 3;
        List<ModelUser> users = userRepository.findAll();
        ArrayList<UUID> reserved = new ArrayList<>();
        Random random = new Random();
        ArrayList<ModelQuestDTO> quests = new ArrayList<>();
        for (ModelUser user : users) {
            boolean hasActive = hasActiveQuest(user.getUserId());
            if (hasActive) {
                continue;
            }
            ArrayList<String> userIdList = new ArrayList<>();
            userIdList.add(user.getUserId().toString());

            for (int i=0; i<2; i++){

                QuestType[] questTypes = QuestType.values();
                QuestType questType = questTypes[random.nextInt(questTypes.length)];
                quests.add(generateQuest(questType,false,userIdList,length));

            }
            if(reserved.contains(user.getUserId())) {continue;}
            if (!user.getFriends().isEmpty()) {
                List<ModelUser> friends = userService.getFriends(user.getUserId()).stream().filter
                        (f -> !hasActiveQuest(f.getUserId())).filter(f -> !reserved.contains(f.getUserId())).toList();
                if (!friends.isEmpty()) {
                    ModelUser friend = friends.get(random.nextInt(friends.size()));
                    List<String> userIds = new ArrayList<>();
                    userIds.add(user.getUserId().toString());
                    userIds.add(friend.getUserId().toString());

                    reserved.add(friend.getUserId());
                    quests.add(generateQuest(null,true,userIds,length));
                }

            }
            ;
        }
        for (ModelQuestDTO quest : quests) {
            save(quest);
        }
    }

    public ModelQuestDTO generateQuest(QuestType questType, boolean friendsQuest, List<String> userIds, int days) {
        Instant now = Instant.now();
        Random random = new Random();
        if (questType == null) {
            QuestType[] questTypes = QuestType.values();
            questType = questTypes[random.nextInt(questTypes.length)];
        }
        if (questType == QuestType.Streak && friendsQuest) {
            questType = random.nextBoolean() ? QuestType.XP : QuestType.CardsLearnt;
        }
        int requestvalue = 0;
        if (questType == QuestType.CardsLearnt) {
            int factor = days * 5;
            requestvalue = random.nextInt(factor- 5, factor);
        }
        if (questType == QuestType.Streak) {
            int factor = days;
            requestvalue = random.nextInt(factor-2, factor);
        }
        if (questType == QuestType.XP) {
            int factor = days*25;
            requestvalue = random.nextInt(factor-25, factor);
        }
        if (friendsQuest) {
            requestvalue = requestvalue * 2;
        }
        return new ModelQuestDTO(UUID.randomUUID(), userIds, questType, now, now.plusSeconds(86400 * days), 0, requestvalue, false, friendsQuest);
    }

    private boolean hasActiveQuest(UUID userId) {
        Instant now = Instant.now();
        return getModelQuestsByUserId(userId).stream()
                .anyMatch(q -> !q.isFinished() && q.getExpiryDate().isAfter(now));
    }

}
