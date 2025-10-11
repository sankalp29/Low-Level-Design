package com.stackoverflow.search;

import java.util.List;

import com.stackoverflow.entity.question.Question;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.person.User;

public interface ISearchable {
    public List<Question> searchByTitle(String text);
    public List<Question> searchByDescription(String text);
    public List<Question> searchByTag(String text);
    public List<Question> searchByAuthor(User author) throws InvalidUserException;
}
