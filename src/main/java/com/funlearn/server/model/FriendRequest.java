package com.funlearn.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.UUID;

@Entity
@IdClass(FriendRequestId.class)
public class FriendRequest {
    @Id
    UUID fromUser;
    @Id
    UUID toUser;
    boolean accepted;

    public FriendRequest(UUID fromUser, UUID toUser, boolean accepted) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.accepted = accepted;
    }

    protected FriendRequest() {

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

    public void setFromUser(UUID fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(UUID toUser) {
        this.toUser = toUser;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
