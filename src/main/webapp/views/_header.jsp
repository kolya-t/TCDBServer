<%--@elvariable id="loggedUser" type="database.pojo.User"--%>
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default navbar-fixed-top text-left" style="text-shadow: none;">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>

        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/">
                    <span class="glyphicon glyphicon-home"></span> Home
                </a></li>
                <li><a href="${pageContext.request.contextPath}/user/list">
                    <span class="glyphicon glyphicon-user"></span> Users
                </a></li>
            </ul>
            <c:choose>
                <c:when test="${loggedUser == null}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="${pageContext.request.contextPath}/signup">
                            <span class="glyphicon glyphicon-user"></span> Sign Up</a>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/login">
                            <span class="glyphicon glyphicon-log-in"></span> Login</a>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a>${loggedUser.login} (${loggedUser.role})</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout">
                            <span class="glyphicon glyphicon-log-out"></span> Log out</a>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>