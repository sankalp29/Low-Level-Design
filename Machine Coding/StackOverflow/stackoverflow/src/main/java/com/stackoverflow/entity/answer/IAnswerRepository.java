package com.stackoverflow.entity.answer;

import java.util.List;

import com.stackoverflow.entity.question.Question;
import com.stackoverflow.person.User;

public interface IAnswerRepository {
    void addAnswer(Answer answer);
    List<Answer> getAnswersForQuestion(Question question); 
    List<Answer> getAnswersByUser(User user);
}
