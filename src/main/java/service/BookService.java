package service;

import model.Book;
import model.BookCopy;

import java.util.HashMap;

public class BookService {
    private HashMap<Book,BookCopy> books = new HashMap<>();
    public BookCopy findBookCopyById(String bookCopyId) {
        String isbn = getIsbnFromBookCopyId(bookCopyId);
        if( isbn == null) {
            return null;
        }

        for (Book book : books.keySet()) {
            if (book.getIsbn().equals(isbn)) {
                for (BookCopy bookCopy : book.getBookCopies()) {
                    if (bookCopy.getCopyId().equals(bookCopyId)) {
                        return bookCopy;
                    }
                }
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
        return bookCopyId.matches(BookCopy.COPY_ID_FORMAT);
    }
}
