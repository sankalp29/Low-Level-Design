package com.stackoverflow;

import java.util.HashSet;

import com.stackoverflow.entity.question.Question;
import com.stackoverflow.exceptions.EmptyQuestionException;
import com.stackoverflow.exceptions.InvalidUserException;
import com.stackoverflow.exceptions.UserMismatchException;
import com.stackoverflow.person.User;

public class Main {
    public static void main(String[] args) {
        singleUserQuestionCreateAndSearch();   
    }

    private static void singleUserQuestionCreateAndSearch() {
        StackOverflowService stackOverflowService = new StackOverflowService();
        User user = new User("Sankalp", "sankalp@email");
        Question question = new Question(user, "title1", "desc1", new HashSet<>());
        try {
            stackOverflowService.addQuestion(user, question); 
        } catch (InvalidUserException | EmptyQuestionException | UserMismatchException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(stackOverflowService.searchByTitle("title1"));
    }
}