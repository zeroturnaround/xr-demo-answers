package com.zeroturnaround.rebelanswers.service;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;

public interface VoteService {

  Vote findForUser(User user, Question question);

  Vote findForUser(User user, Answer answer);

  public boolean store(final Vote vote);

  boolean deleteById(Long id);
}