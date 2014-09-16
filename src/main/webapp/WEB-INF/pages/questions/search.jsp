<%@ include file="../common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../common/head.jspf" %>
  <title>Rebel Answers - Questions</title>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="span12">
      <%@ include file="../common/navbar.jspf" %>

      <div class="content">

        <h1>Search results for '<c:out value="${q}"/>'</h1>

        <c:choose>
          <c:when test="${not empty questions}">
            <c:forEach var="question" items="${questions}">
              <%@ include file="summary.jspf" %>
            </c:forEach>
            <p><util:pagination maxPages="${maxPages}" page="${param['page.page']}" size="${param['page.size']}" preservedParameter="q"/></p>
          </c:when>
          <c:otherwise>
            <c:if test="${empty questions}">
              <p>No questions found.</p>
            </c:if>
          </c:otherwise>
        </c:choose>

      </div>
    </div>
  </div>
</div>

</body>
</html>