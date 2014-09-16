package com.zeroturnaround.rebelanswers.service;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.User;

import java.util.List;

public interface AnswerService {

  public Answer getAnswerById(Long id);

  public List<Answer> getAnswersForAuthor(User user);

  public boolean store(Answer answer);
}