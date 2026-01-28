package com.funlearn.server.service;

import com.funlearn.server.api.model.StudySessionDTO;
import com.funlearn.server.api.model.UserDTO;
import com.funlearn.server.model.ModelUser;
import com.funlearn.server.model.StudySession;
import com.funlearn.server.repository.UserRepository;
import io.swagger.models.Model;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final ZoneId ZONE = ZoneOffset.UTC;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserByUserId(UUID userId) {
        return userRepository.findById(userId).map(this::convertToDTO).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void save(UserDTO userDTO) {
        if (userRepository.existsById(userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A User with this id already exists");
        }
        userRepository.save(this.convertToUser(userDTO));
    }

    private UserDTO convertToDTO(ModelUser user) {
        return new UserDTO(user.getUserId(), user.getUsername(), user.getTotalXP(), user.getTotalCardsLearned(), user.getLastStudyDate(), user.getLevel(), user.getXpToNextLevel(), user.getCurrentStreak(), user.getFriends());
    }

    private ModelUser convertToUser(UserDTO user) {
        return new ModelUser(user.getUserId(), user.getUsername(), user.getTotalXP(), user.getTotalCardsLearned(), user.getLastStudyDate(), user.getLevel(), user.getXpToNextLevel(), user.getCurrentStreak(), user.getFriends());
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public void update(UserDTO userDTO) {
        if (!userRepository.existsById(userDTO.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.save(this.convertToUser(userDTO));

    }

    public void updateWithStudySession(UUID userId, StudySession studySession) {
        final int XP_PER_LEVEL = 25;

        ModelUser user = convertToUser(getUserByUserId(userId));
        int newStreak = calculateStreak(user.getLastStudyDate(), user.getCurrentStreak(), studySession.getTimeStamp());
        user.setCurrentStreak(newStreak);
        user.setLastStudyDate(studySession.getTimeStamp());

        int newTotalXp = user.getTotalXP() + studySession.getXp();
        user.setTotalXP(newTotalXp);
        user.setTotalCardsLearned(user.getTotalCardsLearned() + studySession.getCardsLearnt());

        int newLevel = (newTotalXp / XP_PER_LEVEL) + 1;
        int xpToNextLevel = XP_PER_LEVEL * newLevel;
        user.setLevel(newLevel);
        user.setXpToNextLevel(xpToNextLevel);

        userRepository.save(user);
    }

    private int calculateStreak(Instant lastStudyDate, int oldStreak, Instant newStudyTimestamp) {
        if (lastStudyDate == null) return 1;

        LocalDate last = lastStudyDate.atZone(ZONE).toLocalDate();
        LocalDate current = newStudyTimestamp.atZone(ZONE).toLocalDate();

        long diffDays = ChronoUnit.DAYS.between(last, current);
        if (diffDays == 1) return oldStreak + 1;
        if (diffDays > 1) return 1;
        return oldStreak;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void updateStreak() {
        LocalDate today = LocalDate.now(ZoneOffset.UTC);

        var users = userRepository.findAll();
        for (ModelUser user : users) {
            Instant lastStudyInstant = user.getLastStudyDate();
            if (lastStudyInstant == null) continue;

            LocalDate lastStudyDate = lastStudyInstant.atZone(ZoneOffset.UTC).toLocalDate();
            long daysSince = ChronoUnit.DAYS.between(lastStudyDate, today);

            if (daysSince > 1) {
                user.setCurrentStreak(0);
            }
        }

        userRepository.saveAll(users);
    }
    public boolean existsByUserId(UUID userId) {
        return userRepository.existsById(userId);
    }
    public List<ModelUser> getFriends(UUID userId) {
        ModelUser user = userRepository.getUserByUserId(userId);
        List<UUID> friends = user.getFriends();
        ArrayList<ModelUser> modelUsers = new ArrayList<>();
        for (UUID friend : friends) {
            modelUsers.add(userRepository.getUserByUserId(friend));
        }
        return modelUsers;
    }
}
