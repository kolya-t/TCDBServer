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
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="../_header.jsp"/>
<div class="container-fluid" style="margin-top:100px">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2">
            <div class="table-responsive">
                <table class="table table-condensed table-striped table-bordered">
                    <thead>
                    <tr>
                        <th class="text-right">id</th>
                        <th class="text-center">login</th>
                        <th class="text-center">password</th>
                        <th class="text-center">e-mail</th>
                        <th class="text-center">role</th>
                        <th class="text-center">Действие</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%--@elvariable id="userList" type="java.util.List<database.pojo.User>"--%>
                    <c:if test="${userList != null}">
                        <c:forEach items="${userList}" var="user">
                            <tr>
                                <td class="text-right">${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.password}</td>
                                <td>${user.email}</td>
                                <td>${user.role}</td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/user/edit?id=${user.id}" role="button"
                                       class="btn btn-warning btn-sm">
                                        <span class="glyphicon glyphicon-pencil"></span> Изменить
                                    </a>
                                    <a href="${pageContext.request.contextPath}/user/delete?id=${user.id}" role="button"
                                       class="btn btn-danger btn-sm">
                                        <span class="glyphicon glyphicon-remove"></span> Удалить
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
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
            <jsp:include page="../alerts/success.jsp"/>
            <jsp:include page="../alerts/error.jsp"/>
        </div>
    </div>

    <div class="container text-center">
        <ul class="pagination">
            <%--@elvariable id="offsets" type="java.util.Map<Integer, Integer>"--%>
            <%--@elvariable id="activePage" type="int"--%>
            <c:forEach items="${offsets}" var="offset" varStatus="i">
                <li><a href="${pageContext.request.contextPath}/user/list?offset=${offset.value}"
                       <c:if test="${activePage == offset.key}">class="active"</c:if>>${offset.key}</a></li>
            </c:forEach>
        </ul>
    </div>

</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="../../js/bootstrap.min.js"></script>
</body>
</html>
