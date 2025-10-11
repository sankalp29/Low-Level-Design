package com.stackoverflow.entity.answer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.stackoverflow.entity.question.Question;
import com.stackoverflow.person.User;

public class AnswerRepository implements IAnswerRepository {
    private final ConcurrentHashMap<Question, List<Answer>> answersByQuestion;
    private final ConcurrentHashMap<User, List<Answer>> answersByUser;
    private static AnswerRepository instance;

    @Override
    public void addAnswer(Answer answer) {
        Question question = (Question) answer.getParent();
        answersByQuestion.putIfAbsent(question, new ArrayList<>());
        answersByQuestion.get(question).add(answer);

        answersByUser.putIfAbsent(answer.getAuthor(), new ArrayList<>());
        answersByUser.get(answer.getAuthor()).add(answer);
    }

    @Override
    public List<Answer> getAnswersForQuestion(Question question) {
        return answersByQuestion.getOrDefault(question, new ArrayList<>());
    }

    @Override
    public List<Answer> getAnswersByUser(User user) {
        return answersByUser.getOrDefault(user, new ArrayList<>());
    }

    public static AnswerRepository getInstance() {
        if (instance == null) {
            synchronized (AnswerRepository.class) {
                if (instance == null) instance = new AnswerRepository();
            }
        }

        return instance;
    }

    private AnswerRepository() {
        answersByQuestion = new ConcurrentHashMap<>();
        answersByUser = new ConcurrentHashMap<>();   
    }
}
