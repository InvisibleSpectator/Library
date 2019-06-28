package entity;

import java.util.Objects;

public class AuthorEntity {
    private long authorID;
    private String authorName;


    public AuthorEntity(long authorID, String authorName) {
        this.authorID = authorID;
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getAuthorID() {
        return authorID;
    }

    public void setAuthorID(long authorID) {
        this.authorID = authorID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorEntity)) return false;
        AuthorEntity that = (AuthorEntity) o;
        return authorID == that.authorID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorID);
    }
}
