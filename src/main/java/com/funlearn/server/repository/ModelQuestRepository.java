package com.funlearn.server.repository;

import com.funlearn.server.model.ModelQuest;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.ArrayList;
import java.util.UUID;

public interface ModelQuestRepository extends JpaRepository<ModelQuest, UUID> {
    ModelQuest getByUserIds(ArrayList<String> userIds);
    ModelQuest getByQuestId(UUID questId);
}
