package com.stackoverflow.entity.question;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.exceptions.EmptyQuestionException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;

public interface IQuestionService {
    void addQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException, UserMismatchException;
    void addEntity(Question question, Entity entity);
    void upvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException;
    void undoUpvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException;
    void downvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException;
    void undoDownvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException;
    void updateQuestion(User user, Question question, String title, String desc) throws InvalidUserException, EmptyQuestionException;
}
