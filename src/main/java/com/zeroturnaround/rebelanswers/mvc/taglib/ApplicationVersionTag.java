package com.zeroturnaround.rebelanswers.mvc.taglib;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Properties;

public class ApplicationVersionTag extends TagSupport {
  private String version = null;

  @Override
  public int doStartTag() throws JspException {
    try {
      // even though the access to the version field isn't synchronized, this isn't a problem
      // since the version should never change, it might initialize several times but each time
      // writing the same value
      if (null == version) {
        java.io.InputStream in = pageContext.getServletContext().getResourceAsStream("/META-INF/maven/org.zeroturnaround/xr-demo-answers/pom.properties");
        if (in != null) {
          Properties props = new Properties();
          props.load(in);
          version = (String) props.get("version");
        }
        else {
          version = "x.y";
        }
      }
      pageContext.getOut().print(HtmlUtils.htmlEscape(version));
    }
    catch (final IOException e) {
      throw new JspException("Error: IOException while writing to application version to client" + e.getMessage());
    }
    return SKIP_BODY;
  }
}