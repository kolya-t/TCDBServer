<%--@elvariable id="loggedUser" type="database.pojo.User"--%>
<%--<jsp:forward page="/user/list"/>--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>База данных пользователей</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="http://bootstrap-3.ru/examples/cover/cover.css" rel="stylesheet">
</head>
<body>
<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="masthead clearfix">
                <div class="inner">
                    <%--<h3 class="masthead-brand">Test</h3>--%>
                    <ul class="nav masthead-nav">
                        <li class="active">
                            <a href="#">Home</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/login">Вход</a>
                        </li>
                        <li>
                            <a href="#">Регистрация</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="inner cover">
                <h1 class="cover-heading">Здраствуйте, ${loggedUser.login}</h1>
                <p class="lead">
                    Зарегистрируйтесь или войдите, если уже зарегистрированы, чтобы полноценно пользоваться сервисом
                </p>
                <p class="lead">
                    <a role="button" class="btn btn-default btn-lg" href="${pageContext.request.contextPath}/login">Вход</a>
                    <a role="button" class="btn btn-default btn-lg" href="#">Регистрация</a>
                </p>
            </div>
            <div class="mastfoot">
                <div class="inner">
                    <p>Site by <a href="https://github.com/kolya-t">kolya-t</a> and <a href="http://getbootstrap.com">Bootstrap</a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
