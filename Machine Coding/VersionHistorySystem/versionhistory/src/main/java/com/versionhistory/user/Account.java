package com.versionhistory.user;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Account {
    private final String accountId;
    private final LocalDateTime registeredAt;

    public Account() {
        this.accountId = UUID.randomUUID().toString();
        this.registeredAt = LocalDateTime.now();
    }
}
