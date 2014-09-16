package com.zeroturnaround.rebelanswers.service.impl;

import com.zeroturnaround.rebelanswers.dao.VoteDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;
import com.zeroturnaround.rebelanswers.service.VoteService;
import org.springframework.transaction.annotation.Transactional;

public class DaoVoteService implements VoteService {

  private final VoteDao voteDao;

  public DaoVoteService(final VoteDao voteDao) {
    this.voteDao = voteDao;
  }

  @Transactional(readOnly = true)
  public Vote findForUser(final User user, final Question question) {
    return voteDao.findForUser(user, question);
  }

  @Transactional(readOnly = true)
  public Vote findForUser(final User user, final Answer answer) {
    return voteDao.findForUser(user, answer);
  }

  @Transactional(readOnly = false)
  public boolean store(final Vote vote) {
    if (null == vote) {
      return false;
    }

    voteDao.persistOrMerge(vote);
    return true;
  }

  @Transactional(readOnly = false)
  public boolean deleteById(final Long id) {
    if (null == id) {
      return false;
    }

    return voteDao.deleteById(id);
  }
}
