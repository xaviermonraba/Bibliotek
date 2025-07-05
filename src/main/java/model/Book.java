package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
public class Book {
    @jakarta.persistence.Id
    private String isbn;
    private String title;
    @ManyToOne(cascade = jakarta.persistence.CascadeType.PERSIST)
    private Author author;
    @ManyToOne(cascade = jakarta.persistence.CascadeType.PERSIST)
    private Category category;
    @OneToMany(mappedBy = "book", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<BookCopy> bookCopies;

    public Book() {
        this.bookCopies = new ArrayList<>();
    }

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
        bookCopy.setBook(this);
        this.bookCopies.add(bookCopy);
    }

    public void removeBookCopy(BookCopy bookCopy) {
        this.bookCopies.remove(bookCopy);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Book book))
            return false;
        return this.isbn.equals(book.getIsbn());
    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }
}
