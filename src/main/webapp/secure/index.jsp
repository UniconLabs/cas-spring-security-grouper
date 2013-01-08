<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<body>
<h1>Authenticated User: <span style="color:green;"><sec:authentication property="principal.username" /></span></h1>
<br />

Granted Authorities: <b style="color:green;"><sec:authentication property="authorities"/></b><br />
<sec:authorize ifAllGranted="ss-app:supervisor">
    <p>You are a supervisor! You can therefore see the <a href="extreme/index.jsp">extremely secure page</a>.</p>
</sec:authorize>

<p><a href="../">Home</a>
</body>
</html>