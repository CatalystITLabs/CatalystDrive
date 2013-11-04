<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.File" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href= "./webcontent/resources/css/site.css" type="text/css">
<link rel="stylesheet" href= "./webcontent/resources/css/main.css" type="text/css">
<link rel="stylesheet" href= "./webcontent/resources/css/smoothness/jquery-ui-1.9.0.custom.css" type="text/css">
<script type="text/javascript" src="./webcontent/resources/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/jquery-ui-1.9.0.custom.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/additional-methods.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/main.js"></script>
<script type="text/javascript" src="./webcontent/resources/js/websocket.js"></script>
<!-- Page specific resources -->
<% if (new File(System.getProperty("user.dir") + 
		"/src/main/webapp/webcontent/resources/css" + 
		request.getAttribute("javax.servlet.forward.servlet_path").toString()
		.replace(".html","").substring(request.getAttribute("javax.servlet.forward.servlet_path").toString()
		.lastIndexOf("/")) + ".css").exists()) { %>
	<link rel="stylesheet" href= "./webcontent/resources/css${fn:replace(requestScope['javax.servlet.forward.servlet_path'], '.html', '')}.css" type="text/css">
<% } %>
<% if (new File(System.getProperty("user.dir") + 
		"/src/main/webapp/webcontent/resources/js" + 
		request.getAttribute("javax.servlet.forward.servlet_path").toString()
		.replace(".html","").substring(request.getAttribute("javax.servlet.forward.servlet_path").toString()
		.lastIndexOf("/")) + ".js").exists()) { %>
	<script type="text/javascript" src="./webcontent/resources/js${fn:replace(requestScope['javax.servlet.forward.servlet_path'], '.html', '')}.js"></script>
<% } %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<span id="contextPath" style="display:none;">${pageContext.servletContext.contextPath}</span>
	<span id="serverName" style="display:none;">${pageContext.request.serverName}</span>
	<span id="portNumber" style="display:none;">${pageContext.request.localPort}</span>
	<div class="container"><tiles:insertAttribute name="header" /></div>
	<div class="container"><tiles:insertAttribute name="menu" /></div>
	<div class="container"><tiles:insertAttribute name="body" /></div>
	<div class="container"><tiles:insertAttribute name="footer" /></div>
</body>
</html>