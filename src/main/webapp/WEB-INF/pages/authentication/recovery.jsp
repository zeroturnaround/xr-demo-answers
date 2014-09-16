<%@ include file="../common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../common/head.jspf" %>
  <title>Rebel Answers - Recover your password</title>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="span12">
      <%@ include file="../common/navbar.jspf" %>

      <div class="content">

        <h1>Forgot your password?</h1>

        <p>Tell us the email you used to sign up and we'll get you logged in.</p>

        <form:form modelAttribute="recoveryData" method="post">
          <fieldset>
            <%@ include file="../common/errors.jspf" %>

            <div>
              <form:label path="email">Email</form:label>
              <form:input path="email"/>
            </div>

            <div>
              <button type="submit" class="btn">Reset my password</button>
            </div>
          </fieldset>
        </form:form>

      </div>
    </div>
  </div>
</div>

</body>
</html>