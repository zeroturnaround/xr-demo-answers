package com.zeroturnaround.rebelanswers.dao.jpa;

import com.zeroturnaround.rebelanswers.dao.VoteDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;

import java.util.List;

public class VoteDaoImpl implements VoteDao {

  private final DaoTools daoTools;

  public VoteDaoImpl(final DaoTools daoTools) {
    if (null == daoTools) throw new IllegalArgumentException("daoTools can't be null");
    this.daoTools = daoTools;
  }

  public Vote findForUser(final User user, final Question question) {
    List<Vote> votes = daoTools.findByAttributes(Vote.class, "created", DaoTools.SortOrder.DESC, "author", user, "parentType", Vote.ParentType.QUESTION, "parentId", question.getId());
    if (votes == null || votes.isEmpty()) return null;
    return votes.get(0);
  }

  public Vote findForUser(final User user, final Answer answer) {
    List<Vote> votes = daoTools.findByAttributes(Vote.class, "created", DaoTools.SortOrder.DESC, "author", user, "parentType", Vote.ParentType.ANSWER, "parentId", answer.getId());
    if (votes == null || votes.isEmpty()) return null;
    return votes.get(0);
  }

  public Vote persistOrMerge(final Vote vote) {
    if (null == vote) throw new IllegalArgumentException("vote can't be null");

    if (vote.getId() == null) {
      return daoTools.persist(Vote.class, vote);
    }
    else {
      return daoTools.merge(Vote.class, vote);
    }
  }

  public boolean deleteById(final Long id) {
    if (null == id) return false;

    return daoTools.deleteById(Vote.class, id);
  }
}
