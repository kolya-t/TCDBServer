<%--@elvariable id="loggedUser" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>База данных пользователей</title>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="http://bootstrap-3.ru/examples/cover/cover.css">
</head>
<body>
<jsp:include page="/views/_header.jsp"/>
<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="mastfoot">
                <div class="inner">
                    <p>Site by <a href="https://github.com/kolya-t">kolya-t</a> and <a href="http://getbootstrap.com">Bootstrap</a>
                    </p>
                </div>
            </div>
            <div class="inner cover">
                <h1 class="cover-heading">Здравствуйте, <c:out value="${loggedUser.login}" default="аноним"/></h1>
                <c:if test="${loggedUser == null}">
                    <p class="lead">
                        Войдите или зарегистрируйтесь, чтобы полноценно пользоваться сервисом
                    </p>
                    <p class="lead">
                        <a role="button" class="btn btn-success btn-lg"
                           href="${pageContext.request.contextPath}/login">Вход</a>
                        <a role="button" class="btn btn-primary btn-lg"
                           href="${pageContext.request.contextPath}/signup">Регистрация</a>
                    </p>
                </c:if>
            </div>
        </div>
        <jsp:include page="views/alerts/success.jsp"/>
        <jsp:include page="views/alerts/error.jsp"/>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>
