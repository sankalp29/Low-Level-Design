package com.librarymanagement;

import com.librarymanagement.constants.PaymentMode;
import com.librarymanagement.exceptions.BookIssueLimitReachedException;
import com.librarymanagement.exceptions.BookUnavailableException;
import com.librarymanagement.exceptions.IllegalBookReturnAttempException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Library;
import com.librarymanagement.model.persons.Member;
import com.librarymanagement.payment.PaymentProcessor;

public class App 
{
    public static void main(String[] args) {
        testNonConcurrentBookIssue();
        testConcurrentBookIssue();
    }

    private static void testConcurrentBookIssue() {
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library, new PaymentProcessor(PaymentMode.CARD));
        Member member1 = new Member("Member1", "member1@email.com", "1111111111");
        Member member2 = new Member("Member2", "member2@email.com", "2222222222");
        Member member3 = new Member("Member3", "member3@email.com", "3333333333");

        Book book1 = new Book("Title1", "Desc1", "Author1", "ISBN1", 10);
        Book book2 = new Book("Title2", "Desc2", "Author2", "ISBN2", 20);
        Book book3 = new Book("Title3", "Desc3", "Author3", "ISBN3", 30);
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        Thread thread1 = new Thread(() -> {
            try {
                libraryService.issueBook(member1, book1);
            } catch (BookUnavailableException | BookIssueLimitReachedException ex) {
                System.out.println(ex);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                libraryService.issueBook(member2, book1);
            } catch (BookUnavailableException | BookIssueLimitReachedException ex) {
                System.out.println(ex);
            }
        });
        
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private static void testNonConcurrentBookIssue() {
        Library library = new Library();
        LibraryService libraryService = new LibraryService(library, new PaymentProcessor(PaymentMode.CARD));
        Member member1 = new Member("Member1", "member1@email.com", "1111111111");
        Member member2 = new Member("Member2", "member2@email.com", "2222222222");
        Member member3 = new Member("Member3", "member3@email.com", "3333333333");

        Book book1 = new Book("Title1", "Desc1", "Author1", "ISBN1", 10);
        Book book2 = new Book("Title2", "Desc2", "Author2", "ISBN2", 20);
        Book book3 = new Book("Title3", "Desc3", "Author3", "ISBN3", 30);
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);


        try {
            libraryService.issueBook(member1, book1);
            libraryService.issueBook(member2, book2);
            libraryService.issueBook(member3, book3);
        } catch (BookUnavailableException | BookIssueLimitReachedException ex) {
            System.out.println(ex);
        }

        try {
            libraryService.releaseBook(member1, book1);
        } catch (IllegalBookReturnAttempException ex) {
            System.out.println(ex);
        }

        try {
            libraryService.issueBook(member2, book1);
            libraryService.issueBook(member2, book3);
        } catch (BookUnavailableException | BookIssueLimitReachedException ex) {
            System.out.println(ex);
        }
    }
}
