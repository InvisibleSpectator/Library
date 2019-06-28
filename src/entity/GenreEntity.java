package entity;

import java.util.Objects;

public class GenreEntity {

    private long genreID;
    private String genreTitle;

    public GenreEntity(long genre_id, String genre_title) {
        genreID=genre_id;
        genreTitle=genre_title;
    }

    public String getGenreTitle() {
        return genreTitle;
    }

    public void setGenreTitle(String genreTitle) {
        this.genreTitle = genreTitle;
    }

    public long getGenreID() {
        return genreID;
    }

    public void setGenreID(long genreID) {
        this.genreID = genreID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreEntity)) return false;
        GenreEntity that = (GenreEntity) o;
        return genreID == that.genreID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreID);
    }
}
