package com.zeroturnaround.rebelanswers.dao;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;

public interface VoteDao {

  Vote findForUser(User user, Question question);

  Vote findForUser(User user, Answer answer);

  public Vote persistOrMerge(Vote vote);

  boolean deleteById(Long voteId);
}
