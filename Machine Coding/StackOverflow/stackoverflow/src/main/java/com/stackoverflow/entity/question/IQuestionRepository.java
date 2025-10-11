package com.stackoverflow.entity.question;

import java.util.List;
import java.util.Set;

import com.stackoverflow.person.User;

public interface IQuestionRepository {
    Set<Question> getQuestions();
    void addQuestion(Question question);
    void removeQuestion(Question question);
    List<Question> getQuestionsByUser(User user);
}
