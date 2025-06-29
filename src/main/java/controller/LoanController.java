package controller;

import exceptions.LoanNotAvailableException;
import model.Book;
import model.BookCopy;
import model.Loan;
import service.LoanService;

import java.util.List;

public class LoanController {
    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public List<Loan> getLoanList() {
        return loanService.getLoanList();
    }

    public void lendBookToUser  (String userId, String bookCopyId) throws Exception {
        loanService.lendBookToUser(userId, bookCopyId);
    }
}
