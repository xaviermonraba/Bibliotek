package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Book;
import model.BookCopy;

import java.util.HashMap;
import java.util.Map;

public class BookService {
    //private final Map<String, Book> bookMap;

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotekPU");

    public BookService() {

    }

    public BookCopy findBookCopyById(String bookCopyId) {
        String isbn = getIsbnFromBookCopyId(bookCopyId);
        if (isbn == null) {
            return null;
        }
        EntityManager em = this.getEntityManager();
        Book book = em.find(Book.class, isbn);
        em.close();
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
        EntityManager em = this.getEntityManager();
        Book bookBd = em.find(Book.class, book.getIsbn());
        if (bookBd != null) {
            em.close();
            throw new IllegalArgumentException("Book with this ISBN already exists");
        }
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    public void addBookCopy(BookCopy bookCopy) {
        if (bookCopy == null || bookCopy.getCopyId() == null || bookCopy.getCopyId().isEmpty()) {
            throw new IllegalArgumentException("Book copy or copy ID cannot be null or empty");
        }
        String isbn = getIsbnFromBookCopyId(bookCopy.getCopyId());
        if (isbn == null) {
            throw new IllegalArgumentException("Invalid book copy ID format");
        }
        EntityManager em = this.getEntityManager();
        Book book = em.find(Book.class, isbn);
        if (book == null) {
            em.close();
            throw new IllegalArgumentException("No book found with the given ISBN to add the copy");
        }
        for (BookCopy bookCp : book.getBookCopies()) {
            if (bookCopy.getCopyId().equals(bookCp.getCopyId())) {
                em.close();
                throw new IllegalArgumentException("Book copy with this ID already exists for the book");
            }
        }
        em.getTransaction().begin();
        em.persist(bookCopy);
        em.getTransaction().commit();
        em.close();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void updateBookCopyAvailable(BookCopy bookCopy, boolean b) {
        if (bookCopy == null || bookCopy.getCopyId() == null || bookCopy.getCopyId().isEmpty()) {
            throw new IllegalArgumentException("Book copy or copy ID cannot be null or empty");
        }
        EntityManager em = this.getEntityManager();
        BookCopy existingBookCopy = em.find(BookCopy.class, bookCopy.getCopyId());
        if (existingBookCopy == null) {
            em.close();
            throw new IllegalArgumentException("No book copy found with the given ID to update availability");
        }
        existingBookCopy.setAvailable(b);
        em.getTransaction().begin();
        em.merge(existingBookCopy);
        em.getTransaction().commit();
        em.close();
    }
}