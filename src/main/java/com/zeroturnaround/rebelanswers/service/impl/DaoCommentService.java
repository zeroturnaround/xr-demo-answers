package com.zeroturnaround.rebelanswers.service.impl;

import com.zeroturnaround.rebelanswers.dao.CommentDao;
import com.zeroturnaround.rebelanswers.domain.Comment;
import com.zeroturnaround.rebelanswers.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

public class DaoCommentService implements CommentService {

  private final CommentDao commentDao;

  public DaoCommentService(final CommentDao commentDao) {
    this.commentDao = commentDao;
  }

  @Transactional(readOnly = false)
  public boolean store(final Comment comment) {
    if (null == comment) {
      return false;
    }

    commentDao.persistOrMerge(comment);
    return true;
  }
}
