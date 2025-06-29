package service;

import exceptions.BookNotFoundException;
import exceptions.LoanNotAvailableException;
import exceptions.UserNotFoundException;
import model.BookCopy;
import model.Loan;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    private List<Loan> loanList;
    private BookService bookService;
    private UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.loanList = new ArrayList<>();
        this.bookService = bookService;
        this.userService = userService;
    }
    public LoanService(List<Loan> loanList, BookService bookService) {
        this.loanList = loanList;
        this.bookService = bookService;
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
    }
}