package com.zeroturnaround.rebelanswers.mvc.taglib;

import com.fasterxml.jackson.core.io.JsonStringEncoder;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class HtmlToJsonTag extends BodyTagSupport {
  public int doAfterBody() {
    try {
      BodyContent bodyContent = super.getBodyContent();
      String bodyString = bodyContent.getString();
      JspWriter out = bodyContent.getEnclosingWriter();
      out.write(JsonStringEncoder.getInstance().quoteAsString(bodyString.trim()));

      bodyContent.clear();
    }
    catch (IOException e) {
      System.out.println("Error in HtmlToJsonTag.doAfterBody()" + e.getMessage());
      e.printStackTrace();
    }

    return SKIP_BODY;
  }
}