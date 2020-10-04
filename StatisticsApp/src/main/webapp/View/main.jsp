<%--
  Created by IntelliJ IDEA.
  User: waschbar
  Date: 03-Oct-20
  Time: 6:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <form action="/" method="post">
        <button type="submit" name="button" value="bt1">Click Me!</button>
        <button type="submit" name="button" value="bt2">Click Me Too!</button>
    </form>

    <br>
    <br>

    <%=request.getAttribute("txt")%>

</body>
</html>
