package com.librarymanagement.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import com.librarymanagement.model.Book;

public interface ISearchable {
    public List<Book> findByTitle(String title);
    public List<Book> findByAuthor(String author);
    public List<Book> findByPublicationDate(LocalDateTime publicationDate);
}
