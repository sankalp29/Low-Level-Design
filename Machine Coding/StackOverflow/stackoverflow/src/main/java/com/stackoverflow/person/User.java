package com.stackoverflow.person;

import com.stackoverflow.BadgeType;

import lombok.Getter;

public class User extends Person {
    private Integer badge;
    @Getter
    private BadgeType badgeType;
    
    public User(String name, String email) {
        super(name, email);
        this.badgeType = BadgeType.ROOKIE;
        this.badge = 0;
        this.badgeType = BadgeType.ROOKIE;
    }

    public synchronized void addBadge() {
        badge++;
        if (badge > 10) badgeType = BadgeType.KING;
        else if (badge > 5) badgeType = BadgeType.KNIGHT;
    }
}
