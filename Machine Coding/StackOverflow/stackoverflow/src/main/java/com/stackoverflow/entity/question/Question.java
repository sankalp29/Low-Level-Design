package com.stackoverflow.entity.question;

import java.util.Set;

import com.stackoverflow.entity.Entity;
import com.stackoverflow.entity.EntityType;
import com.stackoverflow.entity.Tag;
import com.stackoverflow.person.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question extends Entity {
    private String title;
	private final Set<Tag> tags;
    private QuestionState questionState;
	
    public Question(User author, String title, String desc, Set<Tag> tags) {
        super(null, author, desc, EntityType.QUESTION);
        this.title = title;
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void close() {
        questionState = QuestionState.CLOSED;
    }

    public void open() {
        questionState = QuestionState.OPEN;
    }
}
