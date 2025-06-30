package model.answer;

import java.util.Map;

public class LoanAnswer {
    public static enum LoanAnswerType {
        LOAN_ON_TIME,
        LOAN_LATE
    }
    private static final Map<LoanAnswerType, String> ANSWER_MESSAGES = Map.of(
        LoanAnswerType.LOAN_ON_TIME, "The book copy has been returned successfully.",
        LoanAnswerType.LOAN_LATE, "The book copy has been returned late. The user has lost points."
    );
    private String userId;
    private String bookCopyId;
    private LoanAnswerType loanAnswerType;

    public LoanAnswer(String userId, String bookCopyId, LoanAnswerType type) {
        this.userId = userId;
        this.bookCopyId = bookCopyId;
        this.loanAnswerType = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(String bookCopyId) {
        this.bookCopyId = bookCopyId;
    }
    public LoanAnswerType getLoanAnswerType() {
        return loanAnswerType;
    }

    public void setLoanAnswerType(LoanAnswerType loanAnswerType) {
        this.loanAnswerType = loanAnswerType;
    }

    @Override
    public String toString() {
        return ANSWER_MESSAGES.get(loanAnswerType) + " User ID: " + userId + ", Book Copy ID: " + bookCopyId;
    }
}
