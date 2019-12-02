<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <title>Add new meal</title>
</head>

<body>
<form method="POST" action='meal' name="frmAddMeal">
    id : <input type="text" readonly="readonly" name="id" value="${meal.id}"/> <br/>
    Description : <input type="text" name="description" value="${meal.description}"/> <br/>
    Calories : <input type="number" name="calories" value="${meal.calories}"/> <br/>
    DateTime : <input type="datetime" name="dateTime" value="${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy hh:mm'))}"/> <br/>
    Exceed : <input type="checkbox" name="exceed" <c:if test="${meal.exceed}"> checked </c:if> /> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>