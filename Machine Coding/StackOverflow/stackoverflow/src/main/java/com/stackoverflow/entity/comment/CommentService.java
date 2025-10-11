package com.stackoverflow.entity.comment;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityValidation;
import com.stackoverflow.entity.Flaggable;
import com.stackoverflow.exceptions.InvalidCommentException;
import com.stackoverflow.person.User;

public class CommentService implements ICommentService, Flaggable {

    private final ICommentRepository commentRepository;

    @Override
    public void addComment(Comment comment) throws InvalidCommentException {
        EntityValidation.isCommentValid(comment);
        commentRepository.addComment(comment);
    }

    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void flag(User user, Entity entity) {
        entity.flag(user);
    }

    public CommentService() {
        commentRepository = CommentRepository.getInstance();
    }
}