<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h3>${date}</h3><br>
<h1>${path}</h1>
<hr>
<%String path = (String) request.getAttribute("path");%>
<a href="<%="?path=" + String.join("/", Arrays.copyOfRange(
                path.split("/"), 0, path.split("/").length - 1
            ))%>">Переместиться назад </a>
<% List<File> directory = (List<File>) request.getAttribute("directoryList");
    for(File e:directory){%>
<h3><a href="<%="?path=" + path + "/" + e.getName()%>"><%=e.getName()%></a></h3>
<%}%>
</body>
</html>