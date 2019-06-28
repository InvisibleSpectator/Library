package servlets;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.GenreDAO;
import entity.AuthorEntity;
import entity.BookEntity;
import entity.GenreEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name="EditBook",urlPatterns = "/EditBook")
public class EditBook extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String oper = req.getParameter("operation");
            req.setAttribute("genres", GenreDAO.selectAll());
            req.setAttribute("authors", AuthorDAO.selectAll());
            BookEntity book;
            if (oper.equals("insert")) {
                book = new BookEntity(0, "", null, new ArrayList<>(), new ArrayList<>());
                req.setAttribute("operation", "insert");
            } else {
                long id = Long.parseLong(req.getParameter("id"));
                book = BookDAO.selectByID(id);
                req.setAttribute("operation", "update");
            }
            req.setAttribute("book", book);

            req.getRequestDispatcher("EditBook.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String oper = req.getParameter("operation");
        Long id = Long.valueOf(req.getParameter("bookID"));
        String title = req.getParameter("title");
        Date date = null;
        String stringDate = req.getParameter("BookYear");
        if (stringDate != "") date = Date.valueOf(stringDate);
        String[] genres = req.getParameterValues("genreID");
        ArrayList<GenreEntity> genreEntityList = new ArrayList<>();
        if (genres != null)
            for (String genre : genres) {
                genreEntityList.add(new GenreEntity(Long.valueOf(genre), null));
            }
        String[] authors = req.getParameterValues("authorID");
        ArrayList<AuthorEntity> authorEntities = new ArrayList<>();
        if (authors != null)
            for (String author : authors) {
                authorEntities.add(new AuthorEntity(Long.valueOf(author), null));
            }
        BookEntity book = new BookEntity(id, title, date, genreEntityList, authorEntities);
        try {
            if (oper.equals("insert"))
                BookDAO.insert(book);
            else
                BookDAO.update(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("Books");
    }
}
