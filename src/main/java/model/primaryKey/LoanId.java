package model.primaryKey;

import jakarta.persistence.*;
import model.BookCopy;
import model.User;

import java.io.Serializable;
import java.util.Calendar;

@Embeddable
public class LoanId implements Serializable {
    @ManyToOne
    private User user;

    @ManyToOne
    private BookCopy bookCopy;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar loanDate;

    public LoanId() {
    }

    public LoanId(User user, BookCopy bookCopy, Calendar loanDate) {
        this.user = user;
        this.bookCopy = bookCopy;
        this.loanDate = loanDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public Calendar getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Calendar loanDate) {
        this.loanDate = loanDate;
    }
}
