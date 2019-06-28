<%@ page import="entity.GenreEntity" %>
<%@ page import="dao.GenreDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.BookEntity" %>
<%@ page import="dao.BookDAO" %>
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
    <title>Библиотека</title>
    <style type="text/css">
        @import url("style/style.css");
    </style>
</head>
<body>
<div class="MainPage">
	<h1>Информационная система библиотеки</h1>
    <a href="Books">Редактирование библиотечного фонда</a><br>
    <a href="Readers">Работа с читателями</a>
</div>
</body>
</html>
