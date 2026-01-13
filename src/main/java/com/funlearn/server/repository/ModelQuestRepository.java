package com.funlearn.server.repository;

import com.funlearn.server.model.ModelQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface ModelQuestRepository extends JpaRepository<ModelQuest, UUID> {
    ModelQuest getByUserIds(List<String> userIds);

    ModelQuest getByQuestId(UUID questId);

    @Query("SELECT mq FROM ModelQuest mq JOIN mq.userIds u WHERE u = :userId")
    List<ModelQuest> getByUserId(@Param("userId") String userId);
}
