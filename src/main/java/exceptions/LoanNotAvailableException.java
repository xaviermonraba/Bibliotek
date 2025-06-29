package exceptions;

public class LoanNotAvailableException extends Exception {
    private static final int CODE_ERROR_LOAN_NOT_AVAILABLE = 1001;
    private static final int CODE_ERROR_BOOK_NOT_FOUND = 1002;
    
    public LoanNotAvailableException(String bookCopyId) {
        super("The book copy with ID " + bookCopyId + " is not available for loan.");
    }

    public LoanNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
