<%--@elvariable id="successMessage" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${successMessage != null}">
    <div class="alert alert-success alert-dismissable">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
        <strong>Успешно!</strong> ${successMessage}
    </div>
    ${successMessage = null}
</c:if>