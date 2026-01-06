package com.funlearn.server.repository;

import com.funlearn.server.model.ModelUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<ModelUser,UUID> {
    ModelUser getUserByUserId(UUID userId);

}
