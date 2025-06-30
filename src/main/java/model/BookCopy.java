package model;

public class BookCopy {
    // A regular expression to validate the format of a copy ID. ISBN_NumberofCopy
    public static final String COPY_ID_FORMAT = "^[A-Za-z0-9]+_[A-Za-z0-9]+$";

    private String copyId;
    private boolean isAvailable;
    private Book book;

    public BookCopy(String copyId, Book book) {
        this.copyId = copyId;
        this.isAvailable = true;
        this.book = book;
    }

    public String getCopyId() {
        return copyId;
    }

    public void setCopyId(String copyId) {
        this.copyId = copyId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
