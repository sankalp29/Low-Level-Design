package com.librarymanagement;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import com.librarymanagement.constants.EntryStatus;
import com.librarymanagement.exceptions.BookIssueLimitReachedException;
import com.librarymanagement.exceptions.BookUnavailableException;
import com.librarymanagement.exceptions.IllegalBookReturnAttempException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.CardEntry;
import com.librarymanagement.model.Library;
import com.librarymanagement.model.LibraryCard;
import com.librarymanagement.model.persons.Member;
import com.librarymanagement.payment.PaymentProcessor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LibraryService {
    private final Library library;
    private final PaymentProcessor paymentProcessor;

    public void addBook(Book book) {
        library.addBook(book);
    }

    public void removeBook(Book book) {
        library.removeBook(book);
    }

    public void issueBook(Member member, Book book) throws BookUnavailableException, BookIssueLimitReachedException {
        LibraryCard libraryCard = member.getLibraryCard();
        
        if (isIssueBookLimitReached(libraryCard)) {
            throw new BookIssueLimitReachedException("You have reached the maximum number of books that can be issued at once");
        }
        book.reserveBook(member);
        libraryCard.createEntry(book);
        System.out.println("Book issued successfully to "+member.getName());
    }

    public void releaseBook(Member member, Book book) throws IllegalBookReturnAttempException {
        LibraryCard libraryCard = member.getLibraryCard();
        Optional<CardEntry> cardEntry = libraryCard.getCardEntry(book);
        if (cardEntry.isEmpty()) throw new IllegalBookReturnAttempException("This book is not issued in your account.");
        Long duration = Duration.between(LocalDateTime.now(), cardEntry.get().getIssueTime()).toNanos();
        double amount = duration * book.getCostPerDay();
        while (!paymentProcessor.processPayment(amount)) {
            System.out.println("Payment failed. Try again");
        }
        book.releaseBook();
        libraryCard.returnBook(book, amount);
    }

    private boolean isIssueBookLimitReached(LibraryCard libraryCard) {
        int booksCurrentlyIssued = 0;
        for (CardEntry entry : libraryCard.getLog()) {
            if (entry.getEntryStatus().equals(EntryStatus.BOOK_RESERVED)) booksCurrentlyIssued++;
        }

        return booksCurrentlyIssued >= 3;
    }
}