<%@ page import="entity.GenreEntity" %>
<%@ page import="dao.GenreDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.BookEntity" %>
<%@ page import="dao.BookDAO" %>
<%@ page import="entity.AuthorEntity" %>
<%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 04.03.2019
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Просмотреть книги</title>
    <style type="text/css" media="screen">
        @import url("style/style.css");
    </style>
</head>
<body>
<h1 align="center">Каталог книг</h1>
<%
    List<BookEntity> books = BookDAO.selectAll();%>
<div class="Actions">
    <label>Навигация</label>
    <form action="EditBook" method="get"><input hidden name="operation" value="insert"><input type="submit"
                                                                                              value="Добавить книгу">
    </form>
    <form action="index.jsp"><input type="submit" value="На главную"/></form>
</div>
<div class="MainBody">
    <table border="1" class="MainTable" id="books">
        <tr>
            <th>ID</th>
            <th>Название книги</th>
            <th>Дата издания</th>
            <th>Жанры</th>
            <th>Авторы</th>
            <th colspan="2">Действия</th>
        </tr>
        <%
            for (BookEntity book : books) {
        %>
        <form action="EditBook" method="get">
            <tr>
                <td class="IDCell"><input hidden value="<%=book.getBookId()%>" name="id"><input hidden
                                                                                                name="operation"
                                                                                                value="update"><%=book.getBookId()%>
                </td>
                <td><%=book.getBookTitle()%>
                </td>
                <td class="DateCell"><%=book.getBookYearOfPublishing()%>
                </td>
                <td class="Subfields">
                    <table class="FieldsTable">
                        <% for (GenreEntity genre : book.getGenreEntities()) {%>
                        <tr>
                            <td>
                                <%=genre.getGenreTitle()%>
                            </td>
                        </tr>
                        <%}%>
                    </table>
                </td >
                <td class="Subfields">
                    <table class="FieldsTable">
                        <% for (AuthorEntity author : book.getAuthorEntities()) {%>
                        <tr>
                            <td>
                                <%=author.getAuthorName()%>
                            </td>
                        </tr>
                        <%}%>
                    </table>
                </td>
                <td class="Action"><input type="submit" value="Редактировать"></td>
                <td class="Action"><input type="button" id="<%=book.getBookId()%>" name="<%=book.getBookTitle()%>" value="Удалить"
                           onclick="deleteBook(this)"></td>
            </tr>
        </form>
        <%}%>
    </table>
</div>
<script>
    function deleteBook(r) {
        var result = confirm("Вы действительно хотите удалить книгу \"" + r.name + "\"");
        if (result) {
            var body = 'id=' + r.id;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "Books");
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            xhr.send(body);
            var i = r.parentNode.parentNode.rowIndex;
            document.getElementById('books').deleteRow(i);
        }
    }
</script>
</body>
</html>
