package controller;// src/test/java/controller/LoanControllerTest.java
import model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoanService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanControllerTest {
    private LoanService loanService;
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        loanService = mock(LoanService.class);
        loanController = new LoanController(loanService);
    }

    @Test
    void testGetLoanList() {
        List<Loan> mockLoans = Arrays.asList(mock(Loan.class), mock(Loan.class));
        when(loanService.getLoanList()).thenReturn(mockLoans);

        List<Loan> result = loanController.getLoanList();

        assertEquals(2, result.size());
        verify(loanService, times(1)).getLoanList();
    }

    @Test
    void testLendBookToUser() throws Exception {
        loanController.lendBookToUser("user1", "copy1");
        verify(loanService, times(1)).lendBookToUser("user1", "copy1");
    }

    @Test
    void testReturnBook() throws Exception {
        loanController.returnBook("user1", "copy1");
        verify(loanService, times(1)).returnBook("user1", "copy1");
    }
}