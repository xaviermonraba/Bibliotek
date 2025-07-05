package service;

import exceptions.BookNotFoundException;
import exceptions.LoanNotAvailableException;
import exceptions.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.BookCopy;
import model.Loan;
import model.User;
import model.answer.LoanAnswer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanService {
    //private final List<Loan> loanList;
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotekPU");
    private final BookService bookService;
    private final UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public List<Loan> getLoanList() {
        EntityManager em = this.getEntityManager();
        try {
            return em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void lendBookToUser(String userId, String bookCopyId) throws Exception {
        BookCopy bookCopy = bookService.findBookCopyById(bookCopyId);
        if(bookCopy == null) {
            throw new BookNotFoundException(bookCopyId);
        }
        if (!bookCopy.isAvailable()) {
            throw new LoanNotAvailableException(bookCopyId);
        }
        User user = this.userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        Loan loan = new Loan(user, bookCopy);
        bookCopy.setAvailable(false);
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        em.persist(loan);
        em.merge(bookCopy);
        em.getTransaction().commit();
    }

    public LoanAnswer returnBook(String userId,String bookCopyId) throws Exception {
        BookCopy bookCopy = bookService.findBookCopyById(bookCopyId);
        if (bookCopy == null) {
            throw new BookNotFoundException(bookCopyId);
        }
        EntityManager em = this.getEntityManager();
        Loan loan = em.createQuery(
                        "SELECT l FROM Loan l " +
                                "Inner Join BookCopy bc on bc.copyId = l.loanId.bookCopy.id " +
                                "WHERE l.loanId.user.id = :userId and l.isReturned = false and bc.id = :bookCopyId",
                        Loan.class)
                .setParameter("userId", userId)
                .setParameter("bookCopyId", bookCopy.getCopyId())
                .getSingleResult();
        if (loan.getBookCopy().getCopyId().equals(bookCopyId) && !loan.isReturned()) {
            em.getTransaction().begin();
            loan.setReturned(true);
            em.merge(loan);
            em.getTransaction().commit();
            bookService.updateBookCopyAvailable(bookCopy, true);
            if(loan.isOverdue()) {
                int penaltyDays = loan.getPenaltyDays();
                userService.updateUserPenaltyDays(userId, penaltyDays);
                return new LoanAnswer(userId, bookCopyId, LoanAnswer.LoanAnswerType.LOAN_LATE);
            }
            else {
                return new LoanAnswer(userId, bookCopyId, LoanAnswer.LoanAnswerType.LOAN_ON_TIME);
            }
        }
        throw new LoanNotAvailableException(bookCopyId);
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}