package servlets;

import dao.IssuedBooksDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ExtendBook", urlPatterns = "/ExtendBook")
public class ExtendBook extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IssuedBooksDAO.extendBook(Long.parseLong(request.getParameter("issueID")));
            Long reader= Long.valueOf(request.getParameter("id"));
            response.sendRedirect("Reader?id="+reader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
