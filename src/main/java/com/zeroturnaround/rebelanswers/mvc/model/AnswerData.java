package com.zeroturnaround.rebelanswers.mvc.model;

import com.zeroturnaround.rebelanswers.domain.Answer;
import org.hibernate.validator.constraints.NotEmpty;

public class AnswerData {

  @NotEmpty
  private String content;

  public AnswerData() {
  }

  public AnswerData(Answer answer) {
    this.content = answer.getContent();
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public AnswerData content(final String content) {
    setContent(content);
    return this;
  }
}
