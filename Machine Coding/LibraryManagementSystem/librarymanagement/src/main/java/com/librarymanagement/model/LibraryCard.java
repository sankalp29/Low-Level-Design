package com.librarymanagement.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.librarymanagement.constants.EntryStatus;

import lombok.Getter;

@Getter
public class LibraryCard {
    private final List<CardEntry> log;

    public LibraryCard() {
        this.log = new ArrayList<>();
    }

    public void createEntry(Book book) {
        this.log.add(new CardEntry(book));
    }

    public void returnBook(Book book, double amount) {
        for (CardEntry entry : log) {
            if (entry.getBookIssued().equals(book) && entry.getEntryStatus().equals(EntryStatus.BOOK_RESERVED)) {
                entry.markReturned(amount);
                return;
            }
        }
    }

    public Optional<CardEntry> getCardEntry(Book book) {
        return log.stream()
            .filter(entry -> entry.getBookIssued().equals(book))
            .findFirst();
    }
}
