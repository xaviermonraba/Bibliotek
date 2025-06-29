package exceptions;

public class BookNotFoundException extends Exception {
    public BookNotFoundException(String bookCopyId) {
        super("The book copy with ID " + bookCopyId + " was not found.");
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
