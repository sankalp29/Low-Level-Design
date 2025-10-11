package com.stackoverflow.entity.answer;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityValidation;
import com.stackoverflow.entity.Flaggable;
import com.stackoverflow.entity.question.IQuestionService;
import com.stackoverflow.entity.question.Question;
import com.stackoverflow.exceptions.EmptyAnswerException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.person.User;

public class AnswerService implements IAnswerService, Flaggable {
    private final IAnswerRepository answerRepository;
    private final IQuestionService questionService;

    @Override
	public void postAnswer(Answer answer) throws InvalidUserException, EmptyAnswerException {
        validateEntities(answer.getAuthor(), answer);
        answerRepository.addAnswer(answer);
        questionService.addEntity((Question) answer.getParent(), answer);
    }

    @Override
	public void upvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException {
        validateEntities(user, answer);
        answer.addUpvote(user);
    }

    @Override
    public void undoUpvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException {
        validateEntities(user, answer);
        answer.removeUpvote(user);
    }

    @Override
	public void downvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException {
        validateEntities(user, answer);
        answer.addDownvote(user);
    }

    @Override
    public void undoDownvoteAnswer(User user, Answer answer) throws InvalidUserException, EmptyAnswerException {
        validateEntities(user, answer);
        answer.removeDownvote(user);
    }

    @Override
    public void flag(User user, Entity entity) {
        entity.flag(user);
    }

    private void validateEntities(User user, Answer answer) throws InvalidUserException, EmptyAnswerException {
        EntityValidation.verifyUserValidity(user);
        EntityValidation.verifyAnswerValidity(answer);
    }

    public AnswerService(IQuestionService questionService) {
        answerRepository = AnswerRepository.getInstance();
        this.questionService = questionService;
    }
}
