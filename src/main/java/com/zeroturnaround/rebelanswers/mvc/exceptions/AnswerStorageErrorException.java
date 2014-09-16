package com.zeroturnaround.rebelanswers.mvc.exceptions;

import com.zeroturnaround.rebelanswers.domain.Answer;

public class AnswerStorageErrorException extends RuntimeException {
  private final Answer answer;

  public AnswerStorageErrorException(final Answer answer) {
    this(answer, null);
  }

  public AnswerStorageErrorException(final Answer answer, final Throwable cause) {
    super("Unexpected error while storing the answer '" + answer + "'.", cause);
    this.answer = answer;
  }

  public Answer getAnswer() {
    return answer;
  }
}