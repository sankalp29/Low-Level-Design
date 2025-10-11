package com.stackoverflow.entity.comment;

import com.stackoverflow.exceptions.InvalidCommentException;

public interface ICommentService {
    void addComment(Comment comment) throws InvalidCommentException;
}
