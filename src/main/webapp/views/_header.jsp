<%--@elvariable id="loggedUser" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <%--<div class="navbar-header">--%>
            <%--<a class="navbar-brand" href="#">WebSiteName</a>--%>
        <%--</div>--%>
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/">
                <span class="glyphicon glyphicon-home"></span> Home
            </a></li>
            <li><a href="${pageContext.request.contextPath}/user/list">
                <span class="glyphicon glyphicon-user"></span> Users
            </a></li>
        </ul>
        <c:if test="${loggedUser == null}">
            <ul class="nav navbar-nav navbar-right">
                <%--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>--%>
                <li><a href="${pageContext.request.contextPath}/login">
                    <span class="glyphicon glyphicon-log-in"></span> Login
                </a></li>
            </ul>
        </c:if>
        <c:if test="${loggedUser != null}">
            <ul class="nav navbar-nav navbar-right">
                <li><p class="navbar-text">${loggedUser.login} (${loggedUser.role})</p></li>
                <li><a href="${pageContext.request.contextPath}/logout"><span class="glyphicon glyphicon-log-out"></span> Log out</a></li>
            </ul>

        </c:if>
    </div>
</nav>