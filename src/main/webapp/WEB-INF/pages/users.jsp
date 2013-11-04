<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h3>Users who have logged into the system:</h3>
<c:forEach var="user" items="${usersModel}">
   <p>${user.username},${user.password}</p>
</c:forEach>
