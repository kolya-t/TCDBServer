<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--@elvariable id="user" type="database.pojo.User"--%>
<html>
<head>
    <title>Создание нового пользователя</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <!-- Bootstrap -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../_header.jsp"/>
<div class="container" style="margin-top: 100px;">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Создание пользователя</h3>
                </div>
                <div class="panel-body">
                    <form role="form" action="${pageContext.request.contextPath}/admin/add" method="post">
                        <div class="form-group">
                            <input class="form-control" placeholder="Login" name="login" type="text"
                                   value="${user.login}" autofocus>
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Пароль" name="password" type="text"
                                   value="${user.password}">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="E-Mail" name="email" type="email"
                                   value="${user.email}">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Role" name="role" type="text" value="${user.role}">
                            <p class="help-block">Например <strong>admin</strong> или <strong>user</strong></p>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Создать</button>
                    </form>
                </div>
            </div>
            <jsp:include page="../alerts/success.jsp"/>
            <jsp:include page="../alerts/error.jsp"/>
        </div>
    </div>
</div>
<c:remove var="user"/>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
