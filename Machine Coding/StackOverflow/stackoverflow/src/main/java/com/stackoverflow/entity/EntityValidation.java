package com.stackoverflow.entity;

import com.stackoverflow.entity.answer.Answer;
import com.stackoverflow.entity.comment.Comment;
import com.stackoverflow.entity.question.Question;
import com.stackoverflow.exceptions.EmptyAnswerException;
import com.stackoverflow.exceptions.EmptyQuestionException;
import com.stackoverflow.exceptions.InvalidCommentException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;

public class EntityValidation {
    public static void verifyUserValidity(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
    }

    public static void verifyQuestionValidity(Question question) throws EmptyQuestionException {
        if (question == null) {
            throw new EmptyQuestionException("Question cannot be null.");
        }
    }

    public static void verifyAnswerValidity(Answer answer) throws EmptyAnswerException {
        if (answer == null) {
            throw new EmptyAnswerException("Answer cannot be null.");
        }
    }

    public static void verifyUserMatch(User user1, User user2) throws UserMismatchException {
        if (!user1.equals(user2)) throw new UserMismatchException("User mismatches with the entity creator");
    }

    public static void isCommentValid(Comment comment) throws InvalidCommentException {
        if (comment == null) throw new InvalidCommentException("Invalid comment");
    }
}
