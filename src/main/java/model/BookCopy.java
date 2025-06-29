package model;

public class BookCopy extends Book {
    private String copyId;
    private boolean isAvailable;

    public BookCopy(String title, Author author, String isbn, Category category, String copyId) {
        super(title, author, isbn, category);
        this.copyId = copyId;
        this.isAvailable = true; // Default to available
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
}
