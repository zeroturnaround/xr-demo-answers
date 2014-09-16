package com.zeroturnaround.rebelanswers.dao;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;

import java.util.Collection;
import java.util.List;

public interface AnswerDao {

  public Answer getAnswerById(Long id);

  public List<Answer> getAnswersForAuthor(User user);

  public Answer persistOrMerge(Answer answer);
}
