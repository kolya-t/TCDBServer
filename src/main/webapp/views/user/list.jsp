<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="userList" type="java.util.List<database.pojo.User>"--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>База данных пользователей</title>
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <table class="table table-condensed table-striped">
            <thead>
            <tr>
                <th class="text-right">id</th>
                <th>login</th>
                <th>name</th>
                <th>password</th>
                <th>e-mail</th>
                <th colspan="2">Действие</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td class="text-right">${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.name}</td>
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/edit?id=${user.id}">
                            <i class="glyphicon glyphicon-pencil"></i> Изменить
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/user/doDelete?id=${user.id}">
                            <i class="glyphicon glyphicon-remove"></i> Удалить
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="7">
                    <a href="${pageContext.request.contextPath}/user/add">
                        <i class="glyphicon glyphicon-plus"></i> Добавить нового пользователя
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
