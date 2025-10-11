package com.stackoverflow.entity.question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.stackoverflow.person.User;

import lombok.Getter;

@Getter
public class QuestionRepository implements IQuestionRepository {
    private final Set<Question> questions;
    private final ConcurrentHashMap<User, List<Question>> questionsByUser;
    private static QuestionRepository instance;

    @Override
    public void addQuestion(Question question) {
        questions.add(question);
        questionsByUser.putIfAbsent(question.getAuthor(), new ArrayList<>());
        questionsByUser.get(question.getAuthor()).add(question);
    }

    @Override
    public void removeQuestion(Question question) {
        questions.remove(question);
        User author = question.getAuthor();
        questionsByUser.get(author).remove(question);
    }

    @Override
    public List<Question> getQuestionsByUser(User user) {
        return new ArrayList<>(questionsByUser.getOrDefault(user, new ArrayList<>()));
    }

    public static QuestionRepository getInstance() {
        if (instance == null) {
            synchronized (QuestionRepository.class) {
                if (instance == null) instance = new QuestionRepository();
            }
        }

        return instance;
    }

    private QuestionRepository() {
        questions = new HashSet<>();
        questionsByUser = new ConcurrentHashMap<>();
    }
}
