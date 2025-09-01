package com.librarymanagement.model;

import java.time.LocalDateTime;

import com.librarymanagement.constants.EntryStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardEntry {
    private final Book bookIssued;
    private final LocalDateTime issueTime;
    private LocalDateTime returnTime;
    private double totalCost;
    private EntryStatus entryStatus;

    public CardEntry(Book bookIssued) {
        this.bookIssued = bookIssued;
        this.issueTime = LocalDateTime.now();
        this.entryStatus = EntryStatus.BOOK_RESERVED;
    }

    public void markReturned(double totalCost) {
        this.totalCost = totalCost;
        this.returnTime = LocalDateTime.now();
        this.entryStatus = EntryStatus.BOOK_RETURNED;
    }
}
