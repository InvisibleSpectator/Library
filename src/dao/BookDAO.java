package dao;

import entity.AuthorEntity;
import entity.BookEntity;
import entity.GenreEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public BookDAO() {
    }

    public static List<BookEntity> selectAll() throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM (((book left JOIN genres_of_book using (book_id)) LEFT JOIN genres using (genre_id)) left join authors_of_book using(book_id)) left join author using (author_id) order by book_id");
            Long id = Long.valueOf(0);
            List<BookEntity> books = new ArrayList<BookEntity>();
            while (set.next()) {
                if (id != set.getLong("book_id")) {
                    books.add(new BookEntity(set.getLong("book_id"), set.getString("book_title"), set.getDate("book_year_of_publishing"), new ArrayList<GenreEntity>(), new ArrayList<AuthorEntity>()));
                    id = books.get(books.size() - 1).getBookId();
                }
                if (set.getLong("author_id") > 0) {
                    AuthorEntity authorEntity = new AuthorEntity(set.getLong("author_id"), set.getString("author_name"));
                    if (!books.get(books.size() - 1).getAuthorEntities().contains(authorEntity))
                        books.get(books.size() - 1).getAuthorEntities().add(authorEntity);

                }
                if (set.getLong("genre_id") > 0) {
                    GenreEntity genreEntity = new GenreEntity(set.getLong("genre_id"), set.getString("genre"));
                    if (!books.get(books.size() - 1).getGenreEntities().contains(genreEntity))
                        books.get(books.size() - 1).getGenreEntities().add(genreEntity);
                }
            }
            return books;
        }
    }

    public static BookEntity selectByID(long bookId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE book_id = ?");
            statement.setLong(1, bookId);
            ResultSet set = statement.executeQuery();
            set.next();
            BookEntity book = new BookEntity(set.getLong("book_id"), set.getString("book_title"), set.getDate("book_year_of_publishing"), null, null);
            statement = connection.prepareStatement("SELECT * FROM (book RIGHT JOIN genres_of_book using (book_id)) LEFT JOIN genres using (genre_id) WHERE book.book_id = ?");
            ArrayList genres = new ArrayList<GenreEntity>();
            statement.setLong(1, bookId);
            set = statement.executeQuery();
            while (set.next()) {
                genres.add(new GenreEntity(set.getLong("genre_id"), set.getString("genre")));
            }
            book.setGenreEntities(genres);
            statement = connection.prepareStatement("SELECT * FROM (book RIGHT JOIN authors_of_book USING (book_id)) LEFT JOIN author USING (author_id) WHERE book.book_id = ?");
            statement.setLong(1, bookId);
            set = statement.executeQuery();
            ArrayList authors = new ArrayList<AuthorEntity>();
            statement.setLong(1, bookId);
            set = statement.executeQuery();
            while (set.next()) {
                authors.add(new AuthorEntity(set.getLong("author_id"), set.getString("author_name")));
            }
            book.setAuthorEntities(authors);
            return book;
        }
    }

    public static void insert(BookEntity bookEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO book (book_title, book_year_of_publishing) VALUES (?,?)", new String[]{"book_id"});
            statement.setString(1, bookEntity.getBookTitle());
            statement.setDate(2, bookEntity.getBookYearOfPublishing());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next()) {
                bookEntity.setBookId(res.getLong("book_id"));
            }
            insertManyToManyFields(bookEntity, connection);
        }
    }

    public static void delete(long bookId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE book_id = ?");
            statement.setLong(1, bookId);
            statement.executeUpdate();
        }
    }

    public static void update(BookEntity bookEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE book SET book_title = ?, book_year_of_publishing = ? WHERE book_id = ?");
            statement.setLong(3, bookEntity.getBookId());
            statement.setString(1, bookEntity.getBookTitle());
            statement.setDate(2, bookEntity.getBookYearOfPublishing());
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM genres_of_book WHERE book_id=?");
            statement.setLong(1, bookEntity.getBookId());
            statement.executeUpdate();
            statement = connection.prepareStatement("DELETE FROM authors_of_book WHERE book_id=?");
            statement.setLong(1, bookEntity.getBookId());
            statement.executeUpdate();
            insertManyToManyFields(bookEntity, connection);
        }
    }

    private static void insertManyToManyFields(BookEntity bookEntity, Connection connection) throws SQLException {
        PreparedStatement statement;
        if (bookEntity.getGenreEntities().size() > 0) {
            statement = connection.prepareStatement("INSERT INTO genres_of_book (genre_id, book_id) VALUES(?,?)");
            for (GenreEntity genre : bookEntity.getGenreEntities()) {
                statement.setLong(1, genre.getGenreID());
                statement.setLong(2, bookEntity.getBookId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
        if (bookEntity.getAuthorEntities().size() > 0) {
            statement = connection.prepareStatement("INSERT INTO authors_of_book (author_id,book_id) VALUES(?,?)");
            for (AuthorEntity author : bookEntity.getAuthorEntities()) {
                statement.setLong(1, author.getAuthorID());
                statement.setLong(2, bookEntity.getBookId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }
}
