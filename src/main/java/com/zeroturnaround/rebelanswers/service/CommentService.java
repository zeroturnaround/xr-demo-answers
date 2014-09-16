package com.zeroturnaround.rebelanswers.service;

import com.zeroturnaround.rebelanswers.domain.Comment;

public interface CommentService {

  public boolean store(Comment comment);

}