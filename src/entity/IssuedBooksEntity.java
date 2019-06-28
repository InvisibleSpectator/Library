package entity;

import java.sql.Date;

public class IssuedBooksEntity {
    private long issueId;
    private long readerId;
    private long bookId;
    private Date issueDate;
    private Date returnDate;
    private String book_title;
    private boolean returned;

    public IssuedBooksEntity(long issue_id, long reader_id, long book_id, Date issue_date, Date return_date, String book_title, boolean returned) {
        issueId = issue_id;
        readerId = reader_id;
        bookId = book_id;
        issueDate = issue_date;
        returnDate = return_date;
        this.book_title = book_title;
        this.returned = returned;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public long getReaderId() {
        return readerId;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
