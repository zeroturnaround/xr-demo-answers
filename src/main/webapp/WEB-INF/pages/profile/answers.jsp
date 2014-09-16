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
          <li class="active">
            <a href="${profileUrl}/answers">Answers</a>
          </li>
          <li>
            <a href="${profileUrl}/questions">Questions</a>
          </li>
        </ul>

        <c:forEach var="answer" items="${answers}">
          <div class="zebra row answer">
            <div class="span1 text-center">
              <span class="label"><c:out value="${answer.voteCount}"/></span>
            </div>
            <div class="span11">
              <spring:url var="questionUrl" value="/question/{id}/{title}">
                <spring:param name="id" value="${answer.question.id}"/>
                <spring:param name="title" value="${ra:sanitizeForUrl(answer.question.title)}"/>
              </spring:url>
              <a href="${questionUrl}"><c:out value="${answer.question.title}"/></a>
            </div>
          </div>
        </c:forEach>

      </div>
    </div>
  </div>
</div>

</body>
</html>