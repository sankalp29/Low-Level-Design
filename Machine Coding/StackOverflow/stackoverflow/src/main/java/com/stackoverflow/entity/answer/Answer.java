package com.stackoverflow.entity.answer;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityType;
import com.stackoverflow.entity.question.Question;
import com.stackoverflow.person.User;

import lombok.Getter;

@Getter
public class Answer extends Entity {
    
    public Answer(Question parent, User answeredBy, String desc) {
        super(parent, answeredBy, desc, EntityType.ANSWER);
        answeredBy.addBadge();
    }
}
