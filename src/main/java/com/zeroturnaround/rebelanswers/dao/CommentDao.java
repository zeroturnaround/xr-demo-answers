package com.zeroturnaround.rebelanswers.dao;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Comment;
import com.zeroturnaround.rebelanswers.domain.Question;

import java.util.List;

public interface CommentDao {

  public List<Comment> getCommentsForQuestion(Question question);

  public List<Comment> getCommentsForAnswer(Answer answer);

  public Comment persistOrMerge(Comment comment);
}
