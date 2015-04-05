<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="math" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>Users</title>
</head>
<body>

<p><a href="${pageContext.request.contextPath}/">Home page</a></p>

<h1>User list</h1>

<form:form method="POST" commandName="user" action="${pageContext.request.contextPath}/user/list/1.html">
    <p>
        Name: <input name=search type=text/>
        <input type="submit" value="find"/>
    </p>

</form:form>

<table style="text-align: center;" border="1px" cellpadding="2" cellspacing="0">
    <thead>
    <tr bgcolor="#faebd7">
        <th width="25px">Id</th>
        <th>Name</th>
        <th>Age</th>
        <th>Admin</th>
        <th>Created date</th>
        <th>Actions</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${userList}">
        <tr>
            <td align="right">${user.id}</td>
            <td align="left">${user.name}</td>
            <td align="right">${user.age}</td>
            <td align="center">${user.isAdmin}</td>
            <td align="center">${user.createdDate}</td>
            <td>
                <a href="${pageContext.request.contextPath}/user/edit/${user.id}.html">Edit</a><br/>
                <a href="${pageContext.request.contextPath}/user/delete/${user.id}.html">Delete</a><br/>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<c:url var="currUrl" value="${pageContext.request.contextPath}/user/list/${currentPageNumber}"/>
<c:url var="firstUrl" value="${pageContext.request.contextPath}/user/list/1"/>
<c:url var="lastUrl" value="${pageContext.request.contextPath}/user/list/${totalPages}"/>

<c:if test="${(currentPageNumber - 1) >= 1}">
    <c:url var="prevUrl" value="${pageContext.request.contextPath}/user/list/${currentPageNumber - 1}"/>
</c:if>

<c:if test="${(currentPageNumber + 1) <= totalPages}">
    <c:url var="nextUrl" value="${pageContext.request.contextPath}/user/list/${currentPageNumber + 1}"/>
</c:if>


<div class="pagination">
    <table style="text-align: center;" border="0px" cellpadding="10" cellspacing="0">
        <tr>

            <c:choose>
                <c:when test="${currentIndex == 1}">
                    <td></td>
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td><a href="${firstUrl}">&lt;&lt;</a></td>
                    <td><a href="${prevUrl}">&lt;</a></td>
                </c:otherwise>
            </c:choose>

            <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
                <c:url var="pageUrl" value="${pageContext.request.contextPath}/user/list/${i}"/>
                <c:choose>
                    <c:when test="${i == currentIndex}">
                        <td bgcolor="aqua"><a href="${pageUrl}"><c:out value="${i}"/></a></td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="${pageUrl}"><c:out value="${i}"/></a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${currentIndex == totalPages}">
                    <td></td>
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td><a href="${nextUrl}">&gt;</a></td>
                    <td><a href="${lastUrl}">&gt;&gt;</a></td>
                </c:otherwise>
            </c:choose>

        </tr>
    </table>
</div>

</body>
</html>