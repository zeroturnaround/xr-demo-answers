package com.zeroturnaround.rebelanswers.mvc.taglib;

import com.zeroturnaround.rebelanswers.mvc.tools.ThreadSafePegDownProcessor;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class MarkdownToHtmlTag extends TagSupport {

  private static ThreadSafePegDownProcessor pegDown;
  private String text;

  public MarkdownToHtmlTag() {
    pegDown = new ThreadSafePegDownProcessor();
  }

  public int doStartTag() throws JspException {
    if (null == text) return SKIP_BODY;

    try {
      pageContext.getOut().print(pegDown.markdownToHtml(text));
    }
    catch (final IOException e) {
      throw new JspException("Error: IOException while writing to client" + e.getMessage());
    }
    return SKIP_BODY;
  }

  public void setText(String text) {
    this.text = text;
  }
}