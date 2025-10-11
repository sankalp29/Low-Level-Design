package com.stackoverflow.entity.comment;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityType;
import com.stackoverflow.person.User;

public class Comment extends Entity {
    public Comment(Entity parent, User author, String desc) {
        super(parent, author, desc, EntityType.COMMENT);
    }
}
