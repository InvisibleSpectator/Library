package servlets;

import dao.ReaderDAO;
import entity.BookEntity;
import entity.ReaderEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "EditReader", urlPatterns = "/EditReader")
public class EditReader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String oper = req.getParameter("operation");
            ReaderEntity readerEntity;
            if (oper.equals("insert")) {
                readerEntity = new ReaderEntity(0, "", "", "", null, null);
                req.setAttribute("operation", "insert");
            } else {
                long id = Long.parseLong(req.getParameter("id"));
                readerEntity = ReaderDAO.selectByID(id);
                req.setAttribute("operation", "update");
            }
            req.setAttribute("reader", readerEntity);

            req.getRequestDispatcher("EditReader.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String oper = req.getParameter("operation");
        Long id = Long.valueOf(req.getParameter("readerID"));
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String patronymic = req.getParameter("patronymic");
        String telephone = req.getParameter("telephone");
        Long tel = null;
        if (!telephone.equals(""))
            tel = Long.valueOf(telephone);
        ReaderEntity readerEntity = new ReaderEntity(id, surname, name, patronymic, null, tel);
        try {
            if (oper.equals("insert"))
                ReaderDAO.insert(readerEntity);
            else
                ReaderDAO.update(readerEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("Readers");
    }
}
