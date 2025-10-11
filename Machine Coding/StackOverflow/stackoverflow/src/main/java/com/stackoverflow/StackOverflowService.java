package com.stackoverflow;

import java.util.List;

import com.stackoverflow.entity.answer.Answer;
import com.stackoverflow.entity.question.Question;
import com.stackoverflow.entity.question.QuestionService;
import com.stackoverflow.exceptions.EmptyAnswerException;
import com.stackoverflow.exceptions.EmptyQuestionException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;
import com.stackoverflow.search.ISearchable;
import com.stackoverflow.search.Search;

public class StackOverflowService {
    private final QuestionService questionService;
	private final ISearchable search;

	public void addQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException, UserMismatchException {
        questionService.addQuestion(user, question);
    }

	public void postAnswer(User user, Question question, Answer answer) throws InvalidUserException, EmptyQuestionException, EmptyAnswerException, UserMismatchException {
        questionService.postAnswer(user, question, answer);
    }

	public void upvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        questionService.upvoteQuestion(user, question);
    }

	public void downvoteQuestion(User user, Question question) throws InvalidUserException, EmptyQuestionException {
        questionService.downvoteQuestion(user, question);
    }

	public void upvoteAnswer(User user, Question question, Answer answer) throws InvalidUserException, EmptyQuestionException {
        questionService.upvoteAnswer(user, question, answer);
    }

    public void undoUpvoteAnswer(User user, Question question, Answer answer) throws InvalidUserException, EmptyQuestionException {
        questionService.undoUpvoteAnswer(user, question, answer);
    }

	public void downvoteAnswer(User user, Question question, Answer answer) throws InvalidUserException, EmptyQuestionException {
        questionService.downvoteAnswer(user, question, answer);
    }

    public void undoDownvoteAnswer(User user, Question question, Answer answer) throws InvalidUserException, EmptyQuestionException {
        questionService.undoDownvoteAnswer(user, question, answer);
    }

	public void updateQuestion(User user, Question question) {
        
    }

	public List<Question> searchByTitle(String text) {
        return search.searchByTitle(text);
    }

    public List<Question> searchByDescription(String text) {
        return search.searchByDescription(text);
    }

    public List<Question> searchByTag(String text) {
        return search.searchByTag(text);
    }

    public List<Question> searchByAuthor(User author) throws InvalidUserException {
        return search.searchByAuthor(author);
    }

    public StackOverflowService() {
        questionService = new QuestionService();
        search = new Search();
    }
}
