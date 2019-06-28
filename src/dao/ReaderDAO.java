package dao;

import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {

    public ReaderDAO() {
    }

    public static List selectAll() throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM reader order by reader_id");
            List list = new ArrayList<>();
            while (set.next())
                list.add(new ReaderEntity(set.getLong("reader_id"), set.getString("reader_surname"), set.getString("reader_name"), set.getString("reader_patronymic"), set.getDate("reader_registration_date"), set.getLong("reader_telephone")));
            return list;
        }
    }

    public static ReaderEntity selectByID(long readerId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reader WHERE reader_id = ?");
            statement.setLong(1, readerId);
            ResultSet set = statement.executeQuery();
            set.next();
            return new ReaderEntity(set.getLong("reader_id"), set.getString("reader_surname"), set.getString("reader_name"), set.getString("reader_patronymic"), set.getDate("reader_registration_date"), set.getLong("reader_telephone"));
        }
    }

    public static void insert(ReaderEntity readerEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO reader (reader_surname, reader_name, reader_patronymic, reader_telephone) VALUES (?,?,?,?)");
            statement.setString(1, readerEntity.getReaderSurname());
            statement.setString(2, readerEntity.getReaderName());
            statement.setString(3, readerEntity.getReaderPatronymic());
            statement.setLong(4, readerEntity.getReaderTelephone());
            statement.executeUpdate();
        }
    }

    public static void delete(long readerId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM reader WHERE reader_id = ?");
            statement.setLong(1, readerId);
            statement.executeUpdate();
        }
    }

    public static void update(ReaderEntity readerEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE reader SET reader_surname = ?,reader_name = ?,reader_patronymic = ?,reader_telephone = ? WHERE reader_id = ?");
            statement.setLong(5, readerEntity.getReaderId());
            statement.setString(1, readerEntity.getReaderSurname());
            statement.setString(2, readerEntity.getReaderName());
            statement.setString(3, readerEntity.getReaderPatronymic());
            statement.setLong(4, readerEntity.getReaderTelephone());
            statement.executeUpdate();
        }
    }

    public static ArrayList<IssuedBooksEntity> getReadersBooks(Long id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("Select * FROM issued_books left join book using(book_id) where reader_id=? order by issue_id desc ");
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            ArrayList<IssuedBooksEntity> books = new ArrayList<IssuedBooksEntity>();
            Long pot = Long.valueOf(0);
            while (set.next()) {
                books.add(new IssuedBooksEntity(set.getLong("issue_id"),set.getLong("reader_id"),set.getLong("book_id"),set.getDate("issue_date"),set.getDate("return_date"), set.getString("book_title"),set.getBoolean("returned")));
            }
            return books;
        }
    }

    public static ArrayList getNonReadersBooks(Long id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("Select book_id, book_title, book_year_of_publishing, genre, author_name, genre_id,author_id from (((((book left JOIN genres_of_book using (book_id)) LEFT JOIN genres using (genre_id)) left join authors_of_book using(book_id)) left join author using (author_id)) LEFT JOIN (select * from issued_books where returned=false and reader_id=?) as T USING (book_id)) where reader_id is null order by book_id");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            Long pot = Long.valueOf(0);
            ArrayList<BookEntity> books = new ArrayList<BookEntity>();
            while (set.next()) {
                if (pot != set.getLong("book_id")) {
                    books.add(new BookEntity(set.getLong("book_id"), set.getString("book_title"), set.getDate("book_year_of_publishing"), new ArrayList<GenreEntity>(), new ArrayList<AuthorEntity>()));
                    pot = books.get(books.size() - 1).getBookId();
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
}
