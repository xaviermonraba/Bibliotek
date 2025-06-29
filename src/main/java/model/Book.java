package model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private Author author;
    private String isbn;
    private Category category;
    private List<BookCopy> bookCopies;

    public Book(String title, Author author, String isbn, Category category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.bookCopies = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public void addBookCopy(BookCopy bookCopy) {
        this.bookCopies.add(bookCopy);
    }

    public void removeBookCopy(BookCopy bookCopy) {
        this.bookCopies.remove(bookCopy);
    }
}
