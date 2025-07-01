package model;

import model.primaryKey.LoanId;

import java.util.Calendar;

@jakarta.persistence.Entity
public class Loan {
    private static final int MAX_LOAN_DAYS = 14; // Maximum days for loan

    @jakarta.persistence.EmbeddedId
    private LoanId loanId;
    private Calendar returnDate;
    private boolean isReturned;

    public Loan() {
    }

    public Loan(User user, BookCopy bookCopy) {
        this.loanId = new LoanId(user, bookCopy, Calendar.getInstance());
        this.returnDate = (Calendar) this.loanId.getLoanDate().clone();
        this.returnDate.add(Calendar.DATE, MAX_LOAN_DAYS);
        this.isReturned = false;
    }

    public User getUser() {
        return loanId.getUser();
    }

    public BookCopy getBookCopy() {
        return loanId.getBookCopy();
    }

    public Calendar getLoanDate() {
        return this.loanId.getLoanDate();
    }

    public void setLoanDate(Calendar loanDate) {
        this.loanId.setLoanDate(loanDate);
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

    public LoanId getLoanId() { return loanId; }

    public void setLoanId(LoanId loanId) { this.loanId = loanId; }

    public boolean isOverdue() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        Calendar normalizedReturnDate = (Calendar) returnDate.clone();
        normalizedReturnDate.set(Calendar.HOUR_OF_DAY, 0);
        normalizedReturnDate.set(Calendar.MINUTE, 0);
        normalizedReturnDate.set(Calendar.SECOND, 0);
        normalizedReturnDate.set(Calendar.MILLISECOND, 0);

        return currentDate.after(normalizedReturnDate);
    }

    public int getPenaltyDays() {
        if (!isOverdue()) {
            return 0; // No penalty if not overdue
        }
        long diffInMillis = Calendar.getInstance().getTimeInMillis() - returnDate.getTimeInMillis();
        return (int) (diffInMillis / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
    }

}
