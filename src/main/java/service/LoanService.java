package service;

import exceptions.BookNotFoundException;
import exceptions.LoanNotAvailableException;
import exceptions.UserNotFoundException;
import model.BookCopy;
import model.Loan;
import model.User;
import model.answer.LoanAnswer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanService {
    private final List<Loan> loanList;
    private final BookService bookService;
    private final UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.loanList = new ArrayList<>();
        this.bookService = bookService;
        this.userService = userService;
    }
    public LoanService(List<Loan> loanList, BookService bookService, UserService userService) {
        this.loanList = loanList;
        this.bookService = bookService;
        this.userService = userService;
    }

    public List<Loan> getLoanList() {
        return loanList;
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
        loanList.add(loan);
    }

    public LoanAnswer returnBook(String userId,String bookCopyId) throws Exception {
        BookCopy bookCopy = bookService.findBookCopyById(bookCopyId);
        if (bookCopy == null) {
            throw new BookNotFoundException(bookCopyId);
        }
        for (Loan loan : loanList) {
            if (loan.getBookCopy().getCopyId().equals(bookCopyId) && !loan.isReturned()) {
                loan.setReturned(true);
                bookCopy.setAvailable(true);
                if(loan.isOverdue()) {
                    User user = userService.getUserById(userId);
                    int penaltyDays = loan.getPenaltyDays();
                    user.setPoints(user.getPoints() - penaltyDays);
                    return new LoanAnswer(userId, bookCopyId, LoanAnswer.LoanAnswerType.LOAN_LATE);
                }
                else {
                    return new LoanAnswer(userId, bookCopyId, LoanAnswer.LoanAnswerType.LOAN_ON_TIME);
                }
            }
        }
        throw new LoanNotAvailableException(bookCopyId);
    }
}