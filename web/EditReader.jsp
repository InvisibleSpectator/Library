<%@ page import="entity.ReaderEntity" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 23.03.2019
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование читателя</title>
    <style type="text/css">
        @import url("style/style.css");
    </style>
</head>
<body>
<form method="post" action="EditReader" id="Edit">
    <%ReaderEntity reader = (ReaderEntity) request.getAttribute("reader");%>
    <input hidden name="operation" value="<%=request.getAttribute("operation")%>">
    <input type="hidden" name="readerID" value="<%=reader.getReaderId()%>">
    <table class="MainTable">

        <tr>
            <td>
                Имя:
            </td>
            <td>
                <input type="text" required autocomplete="off" id="name" name="name"
                       value="<%=reader.getReaderName()%>">
            </td>
        </tr>
        <tr>
            <td>
                Фамилия:
            </td>
            <td>
                <input type="text" required autocomplete="off" id="surname" name="surname"
                       value="<%=reader.getReaderSurname()%>">
            </td>
        </tr>
        <tr>
            <td>
                Отчество:
            </td>
            <td>
                <input type="text" required autocomplete="off" id="patronymic" name="patronymic"
                       value="<%=reader.getReaderPatronymic()%>">
            </td>
        </tr>
        <tr>
            <td>
                Телефон:
            </td>
            <td>
                <input type="tel" autocomplete="off" id="telephone"
                       name="telephone" <%if(reader.getReaderTelephone()==null){%>
                       placeholder="Введите телефон" <%}%> <%if(reader.getReaderTelephone()!=null){%>
                       value="<%=reader.getReaderTelephone()%>"<%}%>>
            </td>
        </tr>
    </table>
</form>
<form id="Cancel" method="get"></form>
<input form="Edit" type="submit" value="Сохранить"><input form="Cancel" type="submit" formaction="Readers"
                                                          value="Отменить">
</body>
</html>
