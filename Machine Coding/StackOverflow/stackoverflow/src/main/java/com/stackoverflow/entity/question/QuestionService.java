package com.stackoverflow.entity.question;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityValidation;
import com.stackoverflow.entity.Flaggable;
import com.stackoverflow.exceptions.EmptyQuestionException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;

public class QuestionService implements IQuestionService, Flaggable {
    private final IQuestionRepository questionRepository;

    @Override
    public void addQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException, UserMismatchException {
        validateEntities(user, question);
        EntityValidation.verifyUserMatch(question.getAuthor(), user);
        questionRepository.addQuestion(question);
    }

    @Override
    public void addEntity(Question question, Entity entity) {
        question.addEntity(entity);
    }

    @Override
	public void upvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        validateEntities(user, question);
        question.addUpvote(user);
        question.removeDownvote(user);
    }

    @Override
    public void undoUpvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        validateEntities(user, question);
        question.removeUpvote(user);
    }

    @Override
	public void downvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        validateEntities(user, question);
        question.addDownvote(user);
        question.removeUpvote(user);
    }

    @Override
    public void undoDownvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        validateEntities(user, question);
        question.removeDownvote(user);
    }

    @Override
	public void updateQuestion(User user, Question question, String title, String desc) throws InvalidUserException, EmptyQuestionException {
        validateEntities(user, question);
        question.setTitle(title);
        question.setDesc(desc);
    }


    @Override
    public void flag(User user, Entity entity) {
        entity.flag(user);
    }

    private void validateEntities(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        EntityValidation.verifyUserValidity(user);
        EntityValidation.verifyQuestionValidity(question);
    }

    public QuestionService() {
        this.questionRepository = QuestionRepository.getInstance();
    }
}
