package model;

import java.util.Calendar;

public class Loan {
    private static final int MAX_LOAN_DAYS = 14; // Maximum days for loan
    private final User user;
    private final Book book;
    private Calendar loanDate;
    private Calendar returnDate;
    private boolean isReturned;

    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.loanDate = Calendar.getInstance();
        this.returnDate = (Calendar) this.loanDate.clone();
        this.returnDate.add(Calendar.DATE, MAX_LOAN_DAYS);
        this.isReturned = false;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Calendar getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Calendar loanDate) {
        this.loanDate = loanDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
