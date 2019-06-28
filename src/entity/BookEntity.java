package entity;

import java.sql.Date;
import java.util.ArrayList;

public class BookEntity {
    private long bookId;
    private String bookTitle;
    private Date bookYearOfPublishing;
    private ArrayList<GenreEntity> genreEntities;
    private ArrayList<AuthorEntity> authorEntities;

    public BookEntity(long bookId, String bookTitle, Date bookYearOfPublishing, ArrayList<GenreEntity> genreEntities, ArrayList<AuthorEntity> authorEntities) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookYearOfPublishing = bookYearOfPublishing;
        this.genreEntities = genreEntities;
        this.authorEntities = authorEntities;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getBookYearOfPublishing() {
        return bookYearOfPublishing;
    }

    public void setBookYearOfPublishing(Date bookYearOfPublishing) {
        this.bookYearOfPublishing = bookYearOfPublishing;
    }

    public ArrayList<GenreEntity> getGenreEntities() {
        return genreEntities;
    }

    public void setGenreEntities(ArrayList<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    public ArrayList<AuthorEntity> getAuthorEntities() {
        return authorEntities;
    }

    public void setAuthorEntities(ArrayList<AuthorEntity> authorEntities) {
        this.authorEntities = authorEntities;
    }

    public ArrayList<Long> getGenreIDs(){
        ArrayList id=new ArrayList();
        for(GenreEntity genre: genreEntities)
            id.add(genre.getGenreID());
        return id;
    }
    public ArrayList<Long> getAuthorsIDs(){
        ArrayList id=new ArrayList();
        for(AuthorEntity author: authorEntities)
            id.add(author.getAuthorName());
        return id;
    }
}
