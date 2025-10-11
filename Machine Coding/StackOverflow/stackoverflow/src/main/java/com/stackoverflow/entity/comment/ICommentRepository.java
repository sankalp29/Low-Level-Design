package com.stackoverflow.entity.comment;

import java.util.List;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.person.User;

public interface ICommentRepository {
    void addComment(Comment comment);
    List<Comment> getCommentsByEntity(Entity entity);
    List<Comment> getCommentsByUser(User user);
}
