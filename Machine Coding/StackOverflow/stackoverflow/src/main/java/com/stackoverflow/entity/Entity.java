package com.stackoverflow.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.stackoverflow.person.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Entity {
    private final UUID id;
    private final EntityType entityType;
    private final Entity parent;
    private final User author;
    private final List<Entity> entities;
    private String desc;
	private final Set<User> upvotes;
	private final Set<User> downvotes;
    private final Set<User> flaggedBy;
    
	private final LocalDateTime postedAt;
    
    public Entity(Entity parent, User author, String desc, EntityType entityType) {
        this.id = UUID.randomUUID();
        this.entityType = entityType;
        this.parent = parent;
        this.entities = new ArrayList<>();
        this.author = author;
        this.desc = desc;
        this.upvotes = new HashSet<>();
        this.downvotes = new HashSet<>();
        this.postedAt = LocalDateTime.now();
        this.flaggedBy = new HashSet<>();
    }

    public void addUpvote(User user) {
        upvotes.add(user);
    }

    public void removeUpvote(User user) {
        upvotes.remove(user);
    }

    public void addDownvote(User user) {
        downvotes.add(user);
    }

    public void removeDownvote(User user) {
        downvotes.remove(user);
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void flag(User user) {
        flaggedBy.add(user);
    }
}
