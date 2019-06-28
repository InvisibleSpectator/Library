package dao;

import entity.IssuedBooksEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssuedBooksDAO {

    public IssuedBooksDAO() {
    }

    public List selectAll() throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM issued_books left join book using(book_id)");
            List list = new ArrayList<>();
            while (set.next())
                list.add(new IssuedBooksEntity(set.getLong("issue_id"), set.getLong("reader_id"), set.getLong("book_id"), set.getDate("issue_date"), set.getDate("return_date"), set.getString("book_title"), set.getBoolean("returned")));
            return list;
        }
    }

    public IssuedBooksEntity selectByID(long Id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM issued_books left join book using(book_id) WHERE issue_id = ?");
            statement.setLong(1, Id);
            ResultSet set = statement.executeQuery();
            return new IssuedBooksEntity(set.getLong("issue_id"), set.getLong("reader_id"), set.getLong("book_id"), set.getDate("issue_date"), set.getDate("return_date"), set.getString("book_title"), set.getBoolean("returned"));
        }
    }

    public void insert(IssuedBooksEntity issuedBooksEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO issued_books (reader_id, book_id) VALUES (?,?)");
            statement.setLong(1, issuedBooksEntity.getReaderId());
            statement.setLong(2, issuedBooksEntity.getBookId());
            statement.executeUpdate();
        }
    }

    public static void insert(ArrayList<IssuedBooksEntity> issuedBooksEntities) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO issued_books (reader_id, book_id) VALUES (?,?)");
            for (IssuedBooksEntity issuedBooksEntity : issuedBooksEntities) {
                statement.setLong(1, issuedBooksEntity.getReaderId());
                statement.setLong(2, issuedBooksEntity.getBookId());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    public void delete(long issueId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM issued_books WHERE issue_id = ?");
            statement.setLong(1, issueId);
            statement.executeUpdate();
        }
    }

    public static void extendBook(long id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE issued_books SET return_date = return_date+14  WHERE issue_id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
    public static void returnBook(long id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE issued_books SET returned = true  WHERE issue_id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
