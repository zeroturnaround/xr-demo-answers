package com.zeroturnaround.rebelanswers.mvc.taglib;

import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.security.SecurityTools;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class AuthenticatedUserNameTag extends TagSupport {
  @Override
  public int doStartTag() throws JspException {
    try {
      User user = SecurityTools.getAuthenticatedUser();
      if (user != null) {
        pageContext.getOut().print(HtmlUtils.htmlEscape(user.getName()));
      }
    }
    catch (final IOException e) {
      throw new JspException("Error: IOException while writing to client" + e.getMessage());
    }
    return SKIP_BODY;
  }
}