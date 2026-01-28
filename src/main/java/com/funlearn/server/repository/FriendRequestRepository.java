package com.funlearn.server.repository;

import com.funlearn.server.model.FriendRequest;
import com.funlearn.server.model.FriendRequestId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, FriendRequestId> {
    List<FriendRequest> findByToUser(UUID toUser);
    List<FriendRequest> findByFromUser(UUID fromUser);
    Optional<FriendRequest> findByFromUserAndToUser(UUID fromUser, UUID toUser);
    boolean existsByFromUserAndToUser(
            UUID fromUser, UUID toUser
    );

}
