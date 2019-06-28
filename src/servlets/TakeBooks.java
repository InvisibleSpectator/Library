package servlets;

import dao.IssuedBooksDAO;
import dao.ReaderDAO;
import entity.IssuedBooksEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "TakeBooks",urlPatterns = "/TakeBooks")
public class TakeBooks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id= Long.parseLong(req.getParameter("id"));
        try {
            req.setAttribute("books", ReaderDAO.getNonReadersBooks(id));
            req.setAttribute("id",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("TakeBooks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String[] books=req.getParameterValues("bookID");
        Long reader= Long.valueOf(req.getParameter("id"));
        if(books!=null) {
            ArrayList<IssuedBooksEntity> ib = new ArrayList<>();
            for (String book : books) {
                ib.add(new IssuedBooksEntity(0, reader, Long.valueOf(book), null, null, null,false));
            }
            try {
                IssuedBooksDAO.insert(ib);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("id",reader);
        resp.sendRedirect("Reader?id="+reader);
    }
}
