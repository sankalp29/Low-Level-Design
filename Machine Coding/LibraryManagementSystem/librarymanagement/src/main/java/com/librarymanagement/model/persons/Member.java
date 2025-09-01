package com.librarymanagement.model.persons;

import java.time.LocalDateTime;

import com.librarymanagement.model.LibraryCard;

import lombok.Getter;

@Getter
public class Member extends Person {
    private final LocalDateTime memberSince;
    private final LibraryCard libraryCard;

    public Member(String name, String email, String phone) {
        super(name, email, phone);
        this.memberSince = LocalDateTime.now();
        this.libraryCard = new LibraryCard();
    }
}
