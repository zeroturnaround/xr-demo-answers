package com.zeroturnaround.rebelanswers.mvc.controller;

import com.zeroturnaround.rebelanswers.dao.AnswerDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Comment;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.mvc.exceptions.CommentStorageErrorException;
import com.zeroturnaround.rebelanswers.security.SecurityTools;
import com.zeroturnaround.rebelanswers.security.StandardAuthorities;
import com.zeroturnaround.rebelanswers.service.CommentService;
import com.zeroturnaround.rebelanswers.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.annotation.security.RolesAllowed;
import java.util.Date;

@Controller
public class CommentController {

  private final QuestionService questionService;
  private final CommentService commentService;
  private final AnswerDao answerService;

  @Autowired
  public CommentController(final QuestionService questionService, final CommentService commentService, final AnswerDao answerService) {
    this.questionService = questionService;
    this.commentService = commentService;
    this.answerService = answerService;
  }

  protected CommentController() {
    // CGLib AOP needs a protected default constructor
    this.questionService = null;
    this.commentService = null;
    this.answerService = null;
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/question/comment/{questionId}", method = RequestMethod.POST)
  public @ResponseBody ModelAndView commentQuestion(@PathVariable final Long questionId, @RequestParam final String comment) throws NoSuchRequestHandlingMethodException {
    Question question = questionService.getQuestionById(questionId);
    if (null == question) {
      throw new NoSuchRequestHandlingMethodException("commentQuestion", this.getClass());
    }
    return performComment(comment, Comment.ParentType.QUESTION, question.getId());
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/answer/comment/{answerId}", method = RequestMethod.POST)
  public @ResponseBody ModelAndView answerQuestion(@PathVariable final Long answerId, @RequestParam final String comment) throws NoSuchRequestHandlingMethodException {
    Answer answer = answerService.getAnswerById(answerId);
    if (null == answer) {
      throw new NoSuchRequestHandlingMethodException("commentAnswer", this.getClass());
    }
    return performComment(comment, Comment.ParentType.ANSWER, answer.getId());
  }

  private ModelAndView performComment(String comment, Comment.ParentType parent_type, Long parent_id) {
    if (null == comment || comment.trim().isEmpty()) {
      return null;
    }
    User user = SecurityTools.getAuthenticatedUser();
    Comment new_comment = new Comment()
        .parentType(parent_type)
        .parentId(parent_id)
        .content(comment)
        .author(user);
    if (!commentService.store(new_comment)) {
      throw new CommentStorageErrorException(new_comment);
    }
    new_comment.setCreated(new Date());

    ModelAndView mav = new ModelAndView("comments/jsondisplay");
    mav.addObject(new_comment);
    return mav;
  }
}