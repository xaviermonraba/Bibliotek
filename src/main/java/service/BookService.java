package service;

import model.Book;
import model.BookCopy;

import java.util.HashMap;
import java.util.Map;

public class BookService {
    private final Map<String, Book> bookMap;

    public BookService() {
        this.bookMap = new HashMap<>();
    }

    public BookCopy findBookCopyById(String bookCopyId) {
        String isbn = getIsbnFromBookCopyId(bookCopyId);
        if (isbn == null) {
            return null;
        }
        Book book = bookMap.get(isbn);
        if (book == null) {
            return null;
        }
        for (BookCopy bookCopy : book.getBookCopies()) {
            if (bookCopy.getCopyId().equals(bookCopyId)) {
                return bookCopy;
            }
        }
        return null;
    }

    private String getIsbnFromBookCopyId(String bookCopyId) {
        if (!isValidBookCopyId(bookCopyId)) {
            return null;
        }
        String[] parts = bookCopyId.split("_");
        return parts[0];
    }

    private boolean isValidBookCopyId(String bookCopyId) {
        if (bookCopyId == null || bookCopyId.isEmpty()) {
            return false;
        }
        return bookCopyId.matches(model.BookCopy.COPY_ID_FORMAT);
    }

    public void addBook(Book book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().isEmpty()) {
            throw new IllegalArgumentException("Book or ISBN cannot be null or empty");
        }
        if (bookMap.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Book with this ISBN already exists");
        }
        bookMap.put(book.getIsbn(), book);
    }

    public void addBookCopy(BookCopy bookCopy) {
        if (bookCopy == null || bookCopy.getCopyId() == null || bookCopy.getCopyId().isEmpty()) {
            throw new IllegalArgumentException("Book copy or copy ID cannot be null or empty");
        }
        String isbn = getIsbnFromBookCopyId(bookCopy.getCopyId());
        if (isbn == null) {
            throw new IllegalArgumentException("Invalid book copy ID format");
        }
        Book book = bookMap.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book found with the given ISBN to add the copy");
        }
        for (BookCopy bookCp : book.getBookCopies()) {
            if (bookCopy.getCopyId().equals(bookCp.getCopyId())) {
                throw new IllegalArgumentException("Book copy with this ID already exists for the book");
            }
        }
        book.addBookCopy(bookCopy);
    }
}