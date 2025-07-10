<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spaces</title>
    <style>
        table {
            border-collapse: collapse;
            width: 50%;
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
<h1>Spaces</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="space" items="${spaces}">
        <tr>
            <td>${space.id}</td>
            <td>${space.name}</td>
        </tr>
    </c:forEach>
</table>

<br>
<a href="${pageContext.request.contextPath}/admin/spaces/create">Create New Space</a><br>
<a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
</body>
</html>
