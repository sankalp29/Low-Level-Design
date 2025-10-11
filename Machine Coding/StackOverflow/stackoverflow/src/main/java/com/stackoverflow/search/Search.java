package com.stackoverflow.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.stackoverflow.entity.Tag;
import com.stackoverflow.entity.question.IQuestionRepository;
import com.stackoverflow.entity.question.Question;
import com.stackoverflow.entity.question.QuestionRepository;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.person.User;

public class Search implements ISearchable {

    private final IQuestionRepository questionRepository;

    @Override
    public List<Question> searchByAuthor(User author) throws InvalidUserException {
        if (author == null) throw new InvalidUserException("Empty author specified. Cannot search by empty author");
        return questionRepository.getQuestionsByUser(author);
    }

    @Override
    public List<Question> searchByDescription(String text) {
        List<Question> matching = new ArrayList<>();

        Set<Question> questions = questionRepository.getQuestions();
        for (Question question : questions) {
            if (question.getDesc().toLowerCase().contains(text.toLowerCase())) {
                matching.add(question);
            }
        }

        return matching;
    }

    @Override
    public List<Question> searchByTitle(String text) {
        List<Question> matching = new ArrayList<>();

        Set<Question> questions = questionRepository.getQuestions();
        for (Question question : questions) {
            if (question.getTitle().toLowerCase().contains(text.toLowerCase())) {
                matching.add(question);
            }
        }

        return matching;
    }

    @Override
    public List<Question> searchByTag(String text) {
        List<Question> matching = new ArrayList<>();

        Set<Question> questions = questionRepository.getQuestions();
        for (Question question : questions) {
            for (Tag tag : question.getTags()) {
                if (tag.getKey().toLowerCase().contains(text.toLowerCase())) {
                    matching.add(question);
                    break;
                }
            }
        }

        return matching;
    }

    public Search() {
        this.questionRepository = QuestionRepository.getInstance();
    }
}
