package com.zeroturnaround.rebelanswers.mvc.model;

import com.zeroturnaround.rebelanswers.domain.Question;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class QuestionData {

  @NotEmpty @Size(max = 120)
  private String title;
  @NotEmpty
  private String content;

  public QuestionData() {
  }

  public QuestionData(Question question) {
    this.title = question.getTitle();
    this.content = question.getContent();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public QuestionData content(final String content) {
    setContent(content);
    return this;
  }
}
