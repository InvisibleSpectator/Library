package dao;

import entity.AuthorEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    public AuthorDAO() {
    }

    public static List selectAll() throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM author");
            List list = new ArrayList<>();
            while (set.next())
                list.add(new AuthorEntity(set.getLong("author_id"), set.getString("author_name")));
            return list;
        }
    }

    public AuthorEntity selectByID(int author_id) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE author_id = ?");
            statement.setLong(1, author_id);
            ResultSet set = statement.executeQuery();
            return new AuthorEntity(set.getLong("author_id"), set.getString("author_name"));
        }
    }

    public void insert(AuthorEntity auE) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO author (author_id, author_name) VALUES (?,?)");
            statement.setLong(1, auE.getAuthorID());
            statement.setString(2, auE.getAuthorName());
            statement.executeUpdate();
        }
    }

    public void delete(int authorId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM author WHERE author_id = ?");
            statement.setLong(1, authorId);
            statement.executeUpdate();
        }
    }

    public void update(AuthorEntity auE) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE author SET author_name = ? WHERE author_id = ?");
            statement.setString(1, auE.getAuthorName());
            statement.setLong(2, auE.getAuthorID());
            statement.executeUpdate();
        }
    }
}
