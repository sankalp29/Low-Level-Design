package com.stackoverflow.entity.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.person.User;

public class CommentRepository implements ICommentRepository {

    private final ConcurrentHashMap<Entity, List<Comment>> commentsByEntity;
    private final ConcurrentHashMap<User, List<Comment>> commentsByUser;
    private static CommentRepository instance;

    @Override
    public void addComment(Comment comment) {
        commentsByEntity.putIfAbsent(comment.getParent(), new ArrayList<>());
        commentsByEntity.get(comment.getParent()).add(comment);

        commentsByUser.putIfAbsent(comment.getAuthor(), new ArrayList<>());
        commentsByUser.get(comment.getAuthor()).add(comment);
    }

    @Override
    public List<Comment> getCommentsByEntity(Entity entity) {
        return commentsByEntity.getOrDefault(entity, new ArrayList<>());
    }

    @Override
    public List<Comment> getCommentsByUser(User user) {
        return commentsByUser.getOrDefault(user, new ArrayList<>());
    }

    public static CommentRepository getInstance() {
        if (instance == null) {
            synchronized (CommentRepository.class) {
                if (instance == null) instance = new CommentRepository();
            }
        }

        return instance;
    }

    private CommentRepository() {
        commentsByEntity = new ConcurrentHashMap<>();
        commentsByUser = new ConcurrentHashMap<>();   
    }
}
