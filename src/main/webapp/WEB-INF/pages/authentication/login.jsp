<%@ include file="../common/taglibs.jspf" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <%@ include file="../common/head.jspf" %>
  <title>Rebel Answers - Log in or Sign up</title>
</head>
<body>

<div class="container">
  <div class="row">
    <div class="span12">
      <%@ include file="../common/navbar.jspf" %>

      <div class="content">

        <div class="row">
          <div class="span4">
            <form action="j_spring_security_check" method="post">
              <fieldset>
                <legend>Log in</legend>

                <p>Please log in to continue.</p>

                <c:if test="${loginError}">
                  <div class="errorSummary">
                    <p>The email or password is not valid.</p>
                  </div>
                </c:if>

                <div>
                  <label for="login_email">Email</label>
                  <input id="login_email" name="j_username" type="content" value="${lastUser}"/>
                </div>

                <div>
                  <label for="login_password">Password</label>
                  <input id="login_password" name="j_password" type="password" value=""/>
                </div>

                <div>
                  <label for="login_rememberme"><input id="login_rememberme" type="checkbox" name="_spring_security_remember_me"/> Remember me</label>
                </div>

                <div>
                  <spring:url var="recoveryUrl" value="/passwordrecovery"/>
                  <a href="${recoveryUrl}">I forgot my password</a>

                  <button type="submit" class="btn">Log me in!</button>
                </div>
              </fieldset>
            </form>

          </div>
          <div class="span4">
            <spring:url var="signupUrl" value="/signup"/>
            <form:form modelAttribute="registrationData" action="${signupUrl}" method="post">
              <fieldset>
                <legend>New to Rebel Answers?</legend>

                <p>A Rebel Answers account is required to continue.</p>

                <%@ include file="../common/errors.jspf" %>

                <div>
                  <form:label path="name">Full name</form:label>
                  <form:input path="name"/>
                </div>

                <div>
                  <form:label path="email">Email</form:label>
                  <form:input path="email"/>
                </div>

                <div>
                  <form:label path="password">Password</form:label>
                  <form:password path="password"/>
                </div>

                <div>
                  <form:label path="verifyPassword">Re-enter password</form:label>
                  <form:password path="verifyPassword"/>
                </div>

                <div>
                  <button type="submit" class="btn">Sign me up!</button>
                </div>
              </fieldset>
            </form:form>
          </div>
          <div class="span4">
            <form>
              <fieldset>
                <legend>Log in with Facebook</legend>

                <p>It's fast and easy.</p>

                <spring:url var="facebookUrl" value="/facebook/connect"/>
                <a href="${facebookUrl}">Log in with Facebook</a>
              </fieldset>
            </form>
          </div>
        </div>

      </div>
    </div>
  </div>
</div>

</body>
</html>