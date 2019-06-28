package dao;

import entity.GenreEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    public GenreDAO() {
    }

    public static List selectAll() throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM genres");
            List list = new ArrayList<>();
            while (set.next())
                list.add(new GenreEntity(set.getLong("genre_id"), set.getString("genre")));
            return list;
        }
    }

    public GenreEntity selectByID(long genreId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM genres WHERE genre_id = ?");
            statement.setLong(1, genreId);
            ResultSet set = statement.executeQuery();
            return new GenreEntity(set.getLong("genre_id"), set.getString("genre"));
        }
    }

    public static void insert(GenreEntity genreEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO genres (genre) VALUES (?)", new String[] {"genre_id"});
            statement.setString(1, genreEntity.getGenreTitle());
            statement.executeUpdate();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next()) {
                System.out.println(res.getLong("genre_id"));
            }
        }
    }

    public void delete(long genreId) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM genres WHERE genre_id = ?");
            statement.setLong(1, genreId);
            statement.executeUpdate();
        }
    }

    public  void update(GenreEntity genreEntity) throws SQLException {
        try (Connection connection = DBConnection.openConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE genres SET genre = ? WHERE genre_id = ?");
            statement.setString(1, genreEntity.getGenreTitle());
            statement.setLong(2, genreEntity.getGenreID());
            statement.executeUpdate();
        }
    }
}
