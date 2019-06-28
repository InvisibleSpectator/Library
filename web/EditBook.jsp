<%@ page import="entity.BookEntity" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.GenreEntity" %>
<%@ page import="entity.AuthorEntity" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 19.03.2019
  Time: 22:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактирование книги</title>
<style type="text/css">
@import url("style/style.css");
</style>
</head>
<body>
<div>
    <form action="EditBook" method="post" id="Confirm">
        <input hidden name="operation" value="<%=request.getAttribute("operation")%>">
        <%
            BookEntity a = (BookEntity) request.getAttribute("book");
            ArrayList<GenreEntity> genres = (ArrayList<GenreEntity>) request.getAttribute("genres");
            ArrayList<AuthorEntity> authors = (ArrayList<AuthorEntity>) request.getAttribute("authors");
        %>

        <table border="1" class="MainTable" >
            <tbody>
            <tr>
                <td>
                    <input hidden name="bookID" value="<%=a.getBookId()%>">Название книги:
                </td>
                <td>
                    <input type="text" name="title" value="<%=a.getBookTitle()%>" required autocomplete="off">
                </td>
            </tr>
            <tr>
                <td>
                    Дата издания:
                </td>
                <td>
                    <input type="date" name="BookYear" max="<%=java.time.LocalDate.now()%>"
                           value="<%=a.getBookYearOfPublishing()%>">
                </td>
            </tr>
            <tr>
                <td>
                    Жанры:
                </td>
                <td>
                    <div>
                        <select id="genresSelect">
                            <%
                                for (GenreEntity genreEntity : genres) {
                                    if (!a.getGenreEntities().contains(genreEntity)) {
                            %>
                            <option value="<%=genreEntity.getGenreID()%>"><%=genreEntity.getGenreTitle()%>
                            </option>
                            <%}%>
                            <%}%>
                        </select>
                        <input type="button" value="Добавить жанр" id="genreConfirm" onclick="addGenreRow()"><br>
                        <table border="1" cellpadding="1" cellspacing="1" class="FieldsTable" id="genres">
                            <tbody>
                            <% for (int i = 0; i < a.getGenreEntities().size(); i++) { %>
                            <tr>
                                <td>
                                    <input type="hidden" hidden id="genreID" name="genreID"
                                           value="<%=a.getGenreEntities().get(i).getGenreID()%>"><%=a.getGenreEntities().get(i).getGenreTitle()%>
                                </td>
                                <td>
                                    <input type="button" id="<%=a.getGenreEntities().get(i).getGenreID()%>"
                                           name="<%=a.getGenreEntities().get(i).getGenreTitle()%>" value="Удалить"
                                           onclick="deleteGenreRow(this)">
                                </td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td>Авторы:
                </td>
                <td>
                    <div>
                        <select id="authorsSelect">
                            <%
                                for (AuthorEntity authorEntity : authors) {
                                    if (!a.getAuthorEntities().contains(authorEntity)) {
                            %>
                            <option value="<%=authorEntity.getAuthorID()%>"><%=authorEntity.getAuthorName()%>
                            </option>
                            <%}%>
                            <%}%>
                        </select>
                        <input type="button" value="Добавить автора" id="authorConfirm" onclick="addAuthorRow()"><br>
                        <table border="1" cellpadding="1" cellspacing="1" class="FieldsTable" id="authors">
                            <tbody>
                            <%
                                for (int i = 0; i < a.getAuthorEntities().size(); i++) {
                            %>
                            <tr>
                                <td>
                                    <input type="hidden" hidden id="authorID" name="authorID"
                                           value="<%=a.getAuthorEntities().get(i).getAuthorID()%>"><%=a.getAuthorEntities().get(i).getAuthorName()%>
                                </td>
                                <td>
                                    <input type="button" value="Удалить"
                                           id="<%=a.getAuthorEntities().get(i).getAuthorID()%>"
                                           name="<%=a.getAuthorEntities().get(i).getAuthorName()%>"
                                           onclick="deleteAuthorRow(this)">
                                </td>
                            </tr>
                            <%}%>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <input type="submit" form="Confirm" value="Сохранить"><input type="submit" form="Cancel" formaction="Books"
                                                                 value="Отменить">
</div>

<form method="get" id="Cancel"></form>
<script>
    function deleteGenreRow(r) {
        var i = r.parentNode.parentNode.rowIndex;
        var newOption = new Option(r.name, r.id);
        var newGenre = document.getElementById("genresSelect");
        newGenre.appendChild(newOption);
        document.getElementById('genres').deleteRow(i);
    }

    function deleteAuthorRow(r) {
        var i = r.parentNode.rowIndex;
        var newOption = new Option(r.name, r.id);
        var newAuthor = document.getElementById("authorsSelect");
        newAuthor.appendChild(newOption);
        document.getElementById('authors').deleteRow(i);
    }

    function addGenreRow() {
        var newGenre = document.getElementById("genresSelect")
        if (newGenre.options.length > 0) {
            var x = document.getElementById('genres').insertRow(0);
            var value = newGenre.options[newGenre.selectedIndex].value;
            var y = x.insertCell(0);
            var z = x.insertCell(1);
            var newHid = document.createElement('input');
            newHid.type = 'hidden';
            newHid.value = value;
            newHid.id = 'genreID';
            newHid.name = 'genreID';
            var genreTitle = newGenre.options[newGenre.selectedIndex].text;
            y.appendChild(newHid);
            y.appendChild(document.createTextNode(genreTitle));
            var newButton = document.createElement('input');
            newButton.type = 'button';
            newButton.value = 'Удалить';
            newButton.onclick = function () {
                deleteGenreRow(this)
            };
            newButton.id = value;
            newButton.name = genreTitle;
            z.appendChild(newButton);
            newGenre.removeChild(newGenre.options[newGenre.selectedIndex]);
        }
    }

    function addAuthorRow() {
        var newAuthor = document.getElementById("authorsSelect");
        if (newAuthor.options.length > 0) {
            var x = document.getElementById('authors').insertRow(0);
            var value = newAuthor.options[newAuthor.selectedIndex].value;
            var y = x.insertCell(0);
            var z = x.insertCell(1);
            var newHid = document.createElement('input');
            newHid.type = 'hidden';
            newHid.value = value;
            newHid.id = 'authorID';
            newHid.name = 'authorID';
            var authorName = newAuthor.options[newAuthor.selectedIndex].text;
            y.appendChild(newHid);
            y.appendChild(document.createTextNode(authorName));
            var newButton = document.createElement('input');
            newButton.type = 'button';
            newButton.value = 'Удалить';
            newButton.onclick = function () {
                deleteAuthorRow(this)
            };
            newButton.id = value;
            newButton.name = authorName;
            z.appendChild(newButton);
            newAuthor.removeChild(newAuthor.options[newAuthor.selectedIndex]);
        }
    }
</script>
</body>
</html>