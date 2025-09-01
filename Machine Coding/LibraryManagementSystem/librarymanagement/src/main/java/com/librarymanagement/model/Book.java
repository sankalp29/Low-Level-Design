package com.librarymanagement.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.librarymanagement.constants.BookStatus;
import com.librarymanagement.exceptions.BookUnavailableException;
import com.librarymanagement.model.persons.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Book {
    private static AtomicInteger bookNumber = new AtomicInteger(0);
    private final Integer id;
    private final String title;
    private final String description;
    private final String author;
    private final String isbn;
    private final Integer costPerDay;
    private final LocalDateTime publicationDate;
    
    @Setter
    private BookStatus bookStatus;

    @Setter
    private Member reservedBy;

    public Book(String title, String description, String author, String isbn, Integer costPerDay) {
        this.id = bookNumber.incrementAndGet();
        this.title = title;
        this.description = description;
        this.author = author;
        this.isbn = isbn;
        this.costPerDay = costPerDay;
        this.publicationDate = LocalDateTime.now();
        this.bookStatus = BookStatus.AVAILABLE;
        this.reservedBy = null;
    }

    public synchronized void reserveBook(Member member) throws BookUnavailableException {
        if (bookStatus.equals(BookStatus.RESERVED)) throw new BookUnavailableException("The book you are trying to reserve is unavailable.");
        this.reservedBy = member;
        bookStatus = BookStatus.RESERVED;
    }

    public void releaseBook() {
        if (bookStatus.equals(BookStatus.AVAILABLE)) {
            System.out.println("The book is already available.");
            return;
        }
        this.reservedBy = null;
        bookStatus = BookStatus.AVAILABLE;
    }
}
