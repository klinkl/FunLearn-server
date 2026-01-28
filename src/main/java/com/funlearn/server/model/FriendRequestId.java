package com.funlearn.server.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
@Embeddable
public class FriendRequestId implements Serializable {
    private UUID fromUser;
    private UUID toUser;

    public FriendRequestId(UUID fromUser, UUID toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    protected FriendRequestId() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequestId that = (FriendRequestId) o;
        return Objects.equals(fromUser, that.fromUser) && Objects.equals(toUser, that.toUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUser, toUser);
    }
}
