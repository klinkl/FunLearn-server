package com.funlearn.server.api.model;

import java.util.UUID;

public class FriendRequestDTO {
    UUID fromUser;
    UUID toUser;
    boolean accepted;

    public FriendRequestDTO(UUID fromUser, UUID toUser, boolean accepted) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.accepted = accepted;
    }

    public UUID getFromUser() {
        return fromUser;
    }

    public UUID getToUser() {
        return toUser;
    }

    public boolean isAccepted() {
        return accepted;
    }
}
