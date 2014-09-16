package com.zeroturnaround.rebelanswers.mvc.taglib;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

public class PrettyTimeTag extends TagSupport {

  private PrettyTime prettyTime;
  private Date date;

  public PrettyTimeTag() {
    prettyTime = new PrettyTime();
  }

  public int doStartTag() throws JspException {
    if (null == date) return SKIP_BODY;

    try {
      pageContext.getOut().print(HtmlUtils.htmlEscape(prettyTime.format(date)));
    }
    catch (final IOException e) {
      throw new JspException("Error: IOException while writing to client" + e.getMessage());
    }
    return SKIP_BODY;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}