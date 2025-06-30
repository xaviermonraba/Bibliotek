package service;

import exceptions.BookNotFoundException;
import exceptions.LoanNotAvailableException;
import exceptions.UserNotFoundException;
import model.BookCopy;
import model.Loan;
import model.User;
import model.answer.LoanAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {
    private BookService bookService;
    private UserService userService;
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        bookService = mock(BookService.class);
        userService = mock(UserService.class);
        loanService = new LoanService(bookService, userService);
    }

    @Test
    void testLendBookToUser_Success() throws Exception {
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);
        when(bookCopy.isAvailable()).thenReturn(true);
        User user = mock(User.class);
        when(userService.getUserById("user1")).thenReturn(user);

        loanService.lendBookToUser("user1", "copy1");

        assertEquals(1, loanService.getLoanList().size());
        verify(bookCopy).setAvailable(false);
    }

    @Test
    void testLendBookToUser_BookNotFound() {
        when(bookService.findBookCopyById("copy1")).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> loanService.lendBookToUser("user1", "copy1"));
    }

    @Test
    void testLendBookToUser_BookNotAvailable() {
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);
        when(bookCopy.isAvailable()).thenReturn(false);

        assertThrows(LoanNotAvailableException.class, () -> loanService.lendBookToUser("user1", "copy1"));
    }

    @Test
    void testLendBookToUser_UserNotFound() {
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);
        when(bookCopy.isAvailable()).thenReturn(true);
        when(userService.getUserById("user1")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> loanService.lendBookToUser("user1", "copy1"));
    }

    @Test
    void testReturnBook_OnTime() throws Exception {
        // Preparar préstamo
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);
        when(bookCopy.isAvailable()).thenReturn(true);
        User user = mock(User.class);
        when(userService.getUserById("user1")).thenReturn(user);

        loanService.lendBookToUser("user1", "copy1");
        Loan loan = loanService.getLoanList().get(0);

        // Simular devolución a tiempo
        loan.setReturned(false);
        when(bookCopy.getCopyId()).thenReturn("copy1");
        loan.setReturned(false);
        loan.setReturnDate(loan.getReturnDate()); // No modificar fecha, no hay retraso

        LoanAnswer answer = loanService.returnBook("user1", "copy1");
        assertEquals(LoanAnswer.LoanAnswerType.LOAN_ON_TIME, answer.getLoanAnswerType());
        verify(bookCopy).setAvailable(true);
    }

    @Test
    void testReturnBook_Late() throws Exception {
        // Preparar préstamo
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);
        when(bookCopy.isAvailable()).thenReturn(true);
        User user = mock(User.class);
        when(userService.getUserById("user1")).thenReturn(user);

        loanService.lendBookToUser("user1", "copy1");
        Loan loan = loanService.getLoanList().get(0);

        // Simular devolución con retraso
        loan.setReturned(false);
        when(bookCopy.getCopyId()).thenReturn("copy1");
        // Manipular la fecha de devolución para que esté vencida
        var overdueDate = Calendar.getInstance();
        overdueDate.add(java.util.Calendar.DATE, -2); // Hace 2 días que venció
        loan.setReturnDate(overdueDate);

        when(user.getPoints()).thenReturn(10);

        LoanAnswer answer = loanService.returnBook("user1", "copy1");
        assertEquals(LoanAnswer.LoanAnswerType.LOAN_LATE, answer.getLoanAnswerType());
        verify(user).setPoints(8); // 10 - 2 días de penalización
        verify(bookCopy).setAvailable(true);
    }

    @Test
    void testReturnBook_BookNotFound() {
        when(bookService.findBookCopyById("copy1")).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> loanService.returnBook("user1", "copy1"));
    }

    @Test
    void testReturnBook_LoanNotAvailable() {
        BookCopy bookCopy = mock(BookCopy.class);
        when(bookService.findBookCopyById("copy1")).thenReturn(bookCopy);

        assertThrows(LoanNotAvailableException.class, () -> loanService.returnBook("user1", "copy1"));
    }
}