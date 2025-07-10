<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Reservation</title>
</head>
<body>
<h1>Create Reservation</h1>

<form method="post" action="${pageContext.request.contextPath}/customer/reservations/create">
    <label for="space">Space:</label>
    <select id="space" name="space.id">
        <c:forEach var="space" items="${spaces}">
            <option value="${space.id}">${space.name}</option>
        </c:forEach>
    </select>
    <br><br>

    <label for="user">User:</label>
    <select id="user" name="user.id">
        <c:forEach var="user" items="${users}">
            <option value="${user.id}">${user.username}</option>
        </c:forEach>
    </select>
    <br><br>

    <label for="time">Time:</label>
    <input type="text" id="time" name="time" placeholder="YYYY-MM-DD HH:MM"/>
    <br><br>

    <button type="submit">Create</button>
</form>

<br>
<a href="${pageContext.request.contextPath}/customer/reservations">Back to Reservations</a>
</body>
</html>
