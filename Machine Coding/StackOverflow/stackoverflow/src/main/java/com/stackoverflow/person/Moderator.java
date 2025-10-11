package com.stackoverflow.person;

import com.stackoverflow.entity.question.Question;

public class Moderator extends Person {

    public Moderator(String name, String email) {
        super(name, email);
    }

    public void closeQuestion(Question question) {
        question.close();
    }

    public void openQuestion(Question question) {
        question.open();
    }
}
