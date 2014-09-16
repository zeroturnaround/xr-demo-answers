package com.zeroturnaround.rebelanswers.service.impl;

import com.zeroturnaround.rebelanswers.dao.AnswerDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.service.AnswerService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DaoAnswerService implements AnswerService {

  private final AnswerDao answerDao;

  public DaoAnswerService(final AnswerDao answerDao) {
    this.answerDao = answerDao;
  }

  @Transactional(readOnly = true)
  public Answer getAnswerById(Long id) {
    return answerDao.getAnswerById(id);
  }

  @Transactional(readOnly = true)
  public List<Answer> getAnswersForAuthor(User user) {
    return answerDao.getAnswersForAuthor(user);
  }

  @Transactional(readOnly = false)
  public boolean store(final Answer answer) {
    if (null == answer) {
      return false;
    }

    answerDao.persistOrMerge(answer);
    return true;
  }
}
