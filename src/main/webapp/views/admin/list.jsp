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
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
</head>
<body>
<jsp:include page="../_header.jsp"/>
<div class="container-fluid" style="margin-top:70px">
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-10 col-md-offset-1 col-lg-8 col-lg-offset-2" id="list-container">
            <div class="input-group">
                <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                <input type="text" id="search-field" class="form-control" placeholder="Search">
            </div>
            <div class="table-responsive" id="table-id">
                <table class="table table-condensed table-striped table-bordered">
                    <thead>
                    <tr>
                        <th class="sort text-right" data-sort="id">id</th>
                        <th class="sort text-center" data-sort="login">login</th>
                        <th class="sort text-center" data-sort="password">password</th>
                        <th class="sort text-center" data-sort="email">e-mail</th>
                        <th class="sort text-center" data-sort="role">role</th>
                        <th class="text-center">Действие</th>
                    </tr>
                    </thead>
                    <tbody class="list">
                    <%--@elvariable id="userList" type="java.util.List<database.pojo.User>"--%>
                    <c:if test="${userList != null}">
                        <c:forEach items="${userList}" var="user">
                            <tr>
                                <td class="id text-right">${user.id}</td>
                                <td class="login">${user.login}</td>
                                <td class="password">${user.password}</td>
                                <td class="email">${user.email}</td>
                                <td class="role">${user.role}</td>
                                <td class="action text-center">
                                    <a href="${pageContext.request.contextPath}/admin/edit?id=${user.id}" role="button"
                                       class="btn btn-warning btn-sm">
                                        <span class="glyphicon glyphicon-pencil"></span> Изменить
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/delete?id=${user.id}" role="button"
                                       class="btn btn-danger btn-sm">
                                        <span class="glyphicon glyphicon-remove"></span> Удалить
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="7">
                            <a href="${pageContext.request.contextPath}/admin/add">
                                <i class="glyphicon glyphicon-plus"></i> Добавить нового пользователя
                            </a>
                        </td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <div class="text-center">
                <ul class="pagination"></ul>
            </div>
            <jsp:include page="../alerts/success.jsp"/>
            <jsp:include page="../alerts/error.jsp"/>
        </div>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/list.js/1.5.0/list.min.js"></script>
<script>
    var userList = new List('list-container', {
        valueNames: ['id', 'login', 'password', 'email', 'role', 'action'],
        page: 10,
        pagination: {
            innerWindow: 1,
            outerWindow: 1
        }
    });

    $('#search-field').on('keyup', function() {
        var searchString = $(this).val();
        userList.search(searchString);
    });
</script>
</body>
</html>
