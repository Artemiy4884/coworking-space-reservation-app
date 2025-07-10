<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Space</title>
</head>
<body>
<h1>Create Space</h1>

<form method="post" action="${pageContext.request.contextPath}/admin/spaces/create">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"/>
    <br><br>
    <button type="submit">Create</button>
</form>

<a href="${pageContext.request.contextPath}/admin/spaces">Back to Spaces</a>
</body>
</html>
