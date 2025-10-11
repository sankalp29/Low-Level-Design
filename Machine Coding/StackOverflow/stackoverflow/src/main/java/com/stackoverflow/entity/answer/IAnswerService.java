package com.stackoverflow.entity.answer;

import com.stackoverflow.exceptions.EmptyAnswerException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;

public interface IAnswerService {
    void postAnswer(Answer answer) throws InvalidUserException, EmptyAnswerException, UserMismatchException;
    void upvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException;
    void undoUpvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException;
    void downvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException;
    void undoDownvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException;
}
