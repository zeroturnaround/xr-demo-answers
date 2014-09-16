<%@ include file="../common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../common/head.jspf" %>
  <title>Rebel Answers - Revise your question</title>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="span12">
      <%@ include file="../common/navbar.jspf" %>

      <div class="content">

        <form:form modelAttribute="questionData" cssClass="form form-horizontal">

          <%@ include file="form.jspf" %>

          <div class="control-group">
            <div class="controls">
              <button type="submit" class="btn">Save revision</button>
              <spring:url var="questionUrl" value="/question/{id}/{title}">
                <spring:param name="id" value="${question.id}"/>
                <spring:param name="title" value="${ra:sanitizeForUrl(question.title)}"/>
              </spring:url>
              <a href="${questionUrl}">Cancel</a>
            </div>
          </div>
        </form:form>

      </div>
    </div>
  </div>
</div>

<%@ include file="../common/markdown.jspf" %>

</body>
</html>