<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Reservations</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<h1>Reservations</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Space</th>
        <th>User</th>
        <th>Time</th>
        <th>Action</th>
    </tr>
    <c:forEach var="reservation" items="${reservations}">
        <tr>
            <td>${reservation.id}</td>
            <td>${reservation.space.name}</td>
            <td>${reservation.user.username}</td>
            <td>${reservation.time}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/reservations/delete/${reservation.id}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
</body>
</html>
