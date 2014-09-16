<%@ include file="../common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../common/head.jspf" %>
  <title>Rebel Answers - Profile</title>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="span12">
      <%@ include file="../common/navbar.jspf" %>

      <div class="content">

        <h1><c:out value="${user.name}"/></h1>

        <spring:url var="profileUrl" value="/profile/{id}/{name}">
          <spring:param name="id" value="${user.id}"/>
          <spring:param name="name" value="${ra:sanitizeForUrl(user.name)}"/>
        </spring:url>
        <ul class="nav nav-tabs">
          <li>
            <a href="${profileUrl}/answers">Answers</a>
          </li>
          <li class="active">
            <a href="${profileUrl}/questions">Questions</a>
          </li>
        </ul>

        <c:forEach var="question" items="${questions}">
          <div class="zebra row question">
            <div class="span1 text-center">
              <span class="label"><c:out value="${question.voteCount}"/></span>
            </div>
            <div class="span11">
              <spring:url var="questionUrl" value="/question/{id}/{title}">
                <spring:param name="id" value="${question.id}"/>
                <spring:param name="title" value="${ra:sanitizeForUrl(question.title)}"/>
              </spring:url>
              <a href="${questionUrl}"><c:out value="${question.title}"/></a>
            </div>
          </div>
        </c:forEach>

      </div>
    </div>
  </div>
</div>

</body>
</html>