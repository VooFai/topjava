<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.lang.String" %>


<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<ul>
    <c:forEach items="${meals_list}" var="meal">
        <%--<c:if test="${meal.exceed}">--%>
        <%--<li><c:out value="${meal}"/></li>--%>
        <%--</c:if>--%>
        <c:choose>
            <c:when test="${meal.exceed}">
                <li style="color:red"><c:out
                        value="${String.format('%s (%s калорий) время: %s',
                        meal.description, meal.calories, meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy hh:mm')))}"/>
                </li>
            </c:when>
            <c:otherwise>
                <li><c:out
                        value="${meal.description} (${meal.calories} калорий) время: ${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy hh:mm'))}"/>
                </li>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</ul>

<table border=1>
    <thead>
    <tr>
        <th>Описание</th>
        <th>Калории</th>
        <th>Дата</th>
        <th colspan="2">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals_list}" var="meal">

        <tr <c:if test="${meal.exceed}">style="color:red"</c:if>>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td><c:out value="${meal.dateTime.format(DateTimeFormatter.ofPattern('dd.MM.yyyy hh:mm'))}"/></td>
                <%--<td><fmt:formatDate pattern="dd.MM.yyyy hh:mm" value="${meal.dateTime}"/></td>--%>


            <td><a href="meal?action=edit&id=<c:out value="${meal.id}"/>">Edit</a></td>
            <td><a href="meal?action=delete&id=<c:out value="${meal.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meal?action=insert">Add Meal</a></p>


</body>
</html>