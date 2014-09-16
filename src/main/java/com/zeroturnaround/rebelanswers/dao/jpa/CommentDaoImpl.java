package com.zeroturnaround.rebelanswers.dao.jpa;

import com.zeroturnaround.rebelanswers.dao.CommentDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Comment;
import com.zeroturnaround.rebelanswers.domain.Question;

import java.util.Collections;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

  private final DaoTools daoTools;

  public CommentDaoImpl(final DaoTools daoTools) {
    if (null == daoTools) throw new IllegalArgumentException("daoTools can't be null");
    this.daoTools = daoTools;
  }

  public List<Comment> getCommentsForQuestion(Question question) {
    if (null == question) {
      return Collections.emptyList();
    }

    return daoTools.findByAttributes(Comment.class, "created", DaoTools.SortOrder.ASC, "parentType", Comment.ParentType.QUESTION, "parentId", question.getId());
  }

  public List<Comment> getCommentsForAnswer(Answer answer) {
    if (null == answer) {
      return Collections.emptyList();
    }

    return daoTools.findByAttributes(Comment.class, "created", DaoTools.SortOrder.ASC, "parentType", Comment.ParentType.ANSWER, "parentId", answer.getId());
  }

  public Comment persistOrMerge(final Comment comment) {
    if (null == comment) throw new IllegalArgumentException("comment can't be null");

    if (comment.getId() == null) {
      return daoTools.persist(Comment.class, comment);
    }
    else {
      return daoTools.merge(Comment.class, comment);
    }
  }
}
