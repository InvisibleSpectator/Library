<%@ page import="entity.BookEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ReaderDAO" %>
<%@ page import="entity.GenreEntity" %>
<%@ page import="entity.AuthorEntity" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 25.03.2019
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Взять книги</title>
<style type="text/css">
@import url("style/style.css");
</style>
</head>
<body>
<%List<BookEntity > books= (List<BookEntity>) request.getAttribute("books");%>
<form action="TakeBooks" method="post">
    <input type="hidden" id="id" name="id" value="<%=request.getAttribute("id")%>">
    <table border="1" class="MainTable">
        <tbody>
        <%for(BookEntity book:books) {%>
        <tr>
            <td><input type="checkbox" name="bookID" value="<%=book.getBookId()%>"></td>
            <td class="IDCell"><%=book.getBookId()%></td>
            <td><%=book.getBookTitle()%></td>
            <td class="DateCell"><%=book.getBookYearOfPublishing()%></td>
            <td>
                <table class="FieldsTable">
                    <% for (GenreEntity genre : book.getGenreEntities()) {%>
                    <tr>
                        <td>
                            <%=genre.getGenreTitle()%>
                        </td>
                    </tr>
                    <%}%>
                </table>
            </td>
            <td>
                <table class="FieldsTable">
                    <% for (AuthorEntity author:book.getAuthorEntities()) {%>
                    <tr>
                        <td>
                            <%=author.getAuthorName()%>
                        </td>
                    </tr>
                    <%}%>
                </table>
            </td>
        </tr>
        <%}%>
        </tbody>
    </table>
    <input type="submit" value="Взять книги">
</form>
</body>
</html>