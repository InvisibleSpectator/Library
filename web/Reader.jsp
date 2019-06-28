<%@ page import="dao.ReaderDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.*" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 25.03.2019
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Читатель</title>
    <style type="text/css">
        @import url("style/style.css");
    </style>
</head>
<body>
<h1 align="center">Читательская карта</h1>
<div class="Actions">
    <label>Навигация</label>
    <input form="Reader" type="submit" value="Взять книги">
    <input type="submit" form="Ret" formaction="Readers" value="К списку">
</div>
<div class="MainBody">
    <%ReaderEntity reader = (ReaderEntity) request.getAttribute("reader");%>
    <table id="readers" class="MainTable" border="1">
        <tbody>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Дата регистрации</th>
            <th>Телефон</th>
        </tr>

        <form id="Reader" action="TakeBooks" method="get">
            <tr>
                <td class="IDCell">
                    <input hidden value="<%=reader.getReaderId()%>" name="id"><%=reader.getReaderId()%>
                </td>
                <td>
                    <%=reader.getReaderSurname()%>
                </td>
                <td>
                    <%=reader.getReaderName()%>
                </td>
                <td>
                    <%=reader.getReaderPatronymic()%>
                </td>
                <td class="DateCell">
                    <%=reader.getReaderRegistrationDate()%>
                </td>
                <td>
                    <%if (reader.getReaderTelephone() != 0) {%>
                    <%=reader.getReaderTelephone()%>
                    <%}%>
                </td>
            </tr>
        </form>
        </tbody>
    </table>
    <h2 align="center">Книги читателя</h2>
    <table class="MainTable" border="1">
        <tbody>
        <%ArrayList<IssuedBooksEntity> books = ReaderDAO.getReadersBooks(reader.getReaderId());%>
        <tr>
            <th>ID</th>
            <th>Название книги</th>
            <th>Дата получения</th>
            <th>Дата возврата</th>
            <th colspan="2">Статус и действия</th>
        </tr>
        <%for (IssuedBooksEntity book : books) {%>
        <form method="post">
            <tr>
                <td class="IDCell"><input hidden value="<%=book.getIssueId()%>" name="issueID"><input hidden
                                                                                                      value="<%=reader.getReaderId()%>"
                                                                                                      name="id"><%=book.getIssueId()%>
                </td>
                <td><%=book.getBook_title()%>
                </td>
                <td class="DateCell"><%=book.getIssueDate()%>
                </td>
                <td class="DateCell"><%=book.getReturnDate()%>
                </td>
                <%if (!book.isReturned()) {%>
                <td class="Action">
                    <input type="submit" value="Сдать книгу" formaction="ReturnBook">
                </td>
                <td class="Action">

                    <%if (book.getReturnDate().before(new java.sql.Date(Calendar.getInstance().getTime().getTime()))) {%>
                    Задолженость
                    <%}%>
                    <%if (book.getReturnDate().after(new java.sql.Date(Calendar.getInstance().getTime().getTime()))) {%>
                    <input type="submit" value="Продлить книгу" formaction="ExtendBook">
                    <%}%>
                </td>
                <%}%>
                <%if (book.isReturned()) {%>
                <td colspan="2" class="Action">Возвращена</td>
                <%}%>
            </tr>
        </form>
        <%}%>
        </tbody>
    </table>
</div>
<form id="Ret"></form>
</body>
</html>
