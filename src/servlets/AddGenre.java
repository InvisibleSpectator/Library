package servlets;

import dao.GenreDAO;
import entity.GenreEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet(name="AddGenre", urlPatterns = "/AddGenre")
public class AddGenre extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String genre=req.getParameter("genre");
        GenreEntity genre1=new GenreEntity(0, genre);
        try {
            GenreDAO.insert(genre1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("AddGenre.jsp").forward(req, resp);
    }
}
