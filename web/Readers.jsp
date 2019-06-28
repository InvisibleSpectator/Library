<%@ page import="entity.ReaderEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ReaderDAO" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 23.03.2019
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Читатели</title>
    <style type="text/css">
        @import url("style/style.css");
    </style>
</head>
<body>
<h1 align="center">Список читателей</h1>
<div class="Actions">
    <label>Навигация</label>
    <input type="submit" form="Insert" value="Добавить читателя">
    <input type="submit" form="Main" value="На главную"/>
</div>
<div class="MainBody">
    <%List<ReaderEntity> readers = ReaderDAO.selectAll();%>
    <table id="readers" border="1" class="MainTable">
        <tbody>
        <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Отчество</th>
            <th>Дата регистрации</th>
            <th>Телефон</th>
            <th colspan="3">Действия</th>
        </tr>
        <%for (ReaderEntity reader : readers) {%>
        <tr>
            <form method="get">
                <td class="IDCell">
                    <input hidden value="<%=reader.getReaderId()%>" name="id"><input hidden name="operation"
                                                                                     value="update">
                    <%=reader.getReaderId()%>
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
                <td class="Action">
                    <input type="submit" formaction="EditReader" value="Редактировать"></input>
                </td>
                <td class="Action"><input type="button" id="<%=reader.getReaderId()%>" value="Удалить"
                                          onclick="deleteReader(this)">

                </td>
                <td class="Action">
                    <input type="submit" formaction="Reader" value="Выбрать"></input>
                </td>
            </form>
        </tr>
        <%}%>
        </tbody>
    </table>

</div>
<form action="EditReader" id="Insert" method="get"><input hidden name="operation" value="insert"></form>
<form action="index.jsp" id="Main"></form>
<script>
    function deleteReader(r) {
        var result = confirm("Вы действительно хотите удалить читателя");
        if (result) {
            var body = 'id=' + r.id;
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "Readers");
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            xhr.send(body);
            var i = r.parentNode.parentNode.rowIndex;
            document.getElementById('readers').deleteRow(i);
        }
    }
</script>
</body>
</html>
