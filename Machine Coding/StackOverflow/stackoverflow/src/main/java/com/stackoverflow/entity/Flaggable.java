package com.stackoverflow.entity;

import com.stackoverflow.person.User;

public interface Flaggable {
    void flag(User user, Entity entity);
}
