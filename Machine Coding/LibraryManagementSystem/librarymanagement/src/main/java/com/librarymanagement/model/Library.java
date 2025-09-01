package com.librarymanagement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.librarymanagement.interfaces.ISearchable;

import lombok.Getter;

@Getter
public class Library implements ISearchable {
    private final List<Book> books;
    private Map<String, List<Book>> booksByAuthor;
    private Map<String, List<Book>> booksByPublicationDate;
    private Map<String, List<Book>> booksByTitle;

    public Library() {
        books = new ArrayList<>();
        booksByAuthor = new HashMap<>();
        booksByPublicationDate = new HashMap<>();
        booksByTitle = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);

        booksByAuthor.put(book.getAuthor(), booksByAuthor.getOrDefault(book.getAuthor(), new ArrayList<>()));
        booksByAuthor.get(book.getAuthor()).add(book);
        booksByPublicationDate.put(book.getPublicationDate().toString(), booksByPublicationDate.getOrDefault(book.getPublicationDate().toString(), new ArrayList<>()));
        booksByPublicationDate.get(book.getPublicationDate().toString()).add(book);
        booksByTitle.put(book.getTitle(), booksByTitle.getOrDefault(book.getTitle(), new ArrayList<>()));
        booksByTitle.get(book.getTitle()).add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
        booksByAuthor.get(book.getAuthor()).remove(book);
        booksByPublicationDate.get(book.getPublicationDate().toString()).remove(book);
        booksByTitle.get(book.getTitle()).remove(book);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return booksByTitle.getOrDefault(title, new ArrayList<>());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return booksByAuthor.getOrDefault(author, new ArrayList<>());
    }

    @Override
    public List<Book> findByPublicationDate(LocalDateTime publicationDate) {
        return booksByPublicationDate.getOrDefault(publicationDate.toString(), new ArrayList<>());
    }
}
