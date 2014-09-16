package com.zeroturnaround.rebelanswers.mvc.controller;

import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;
import com.zeroturnaround.rebelanswers.security.SecurityTools;
import com.zeroturnaround.rebelanswers.security.StandardAuthorities;
import com.zeroturnaround.rebelanswers.service.AnswerService;
import com.zeroturnaround.rebelanswers.service.QuestionService;
import com.zeroturnaround.rebelanswers.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@Controller
public class VoteController {

  private final VoteService voteService;
  private final QuestionService questionService;
  private final AnswerService answerService;

  @Autowired
  public VoteController(VoteService voteService, final QuestionService questionService, AnswerService answerService) {
    this.voteService = voteService;
    this.questionService = questionService;
    this.answerService = answerService;
  }

  protected VoteController() {
    // CGLib AOP needs a protected default constructor
    this.voteService = null;
    this.questionService = null;
    this.answerService = null;
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/question/voteup/{questionId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Integer> voteUpQuestion(@PathVariable final Long questionId) throws NoSuchRequestHandlingMethodException {
    Question question = questionService.getQuestionById(questionId);
    if (null == question) {
      throw new NoSuchRequestHandlingMethodException("voteUpQuestion", this.getClass());
    }

    return performQuestionVote(question, SecurityTools.getAuthenticatedUser(), Vote.UP);
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/question/votedown/{questionId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Integer> voteDownQuestion(@PathVariable final Long questionId) throws NoSuchRequestHandlingMethodException {
    Question question = questionService.getQuestionById(questionId);
    if (null == question) {
      throw new NoSuchRequestHandlingMethodException("voteDownQuestion", this.getClass());
    }

    return performQuestionVote(question, SecurityTools.getAuthenticatedUser(), Vote.DOWN);
  }

  private Map<String, Integer> performQuestionVote(Question question, User user, int type) {
    Vote vote = voteService.findForUser(user, question);
    return performVote(user, type, vote, question.getVoteCount(), question.getId(), Vote.ParentType.QUESTION);
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/answer/voteup/{answerId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Integer> voteUpAnswer(@PathVariable final Long answerId) throws NoSuchRequestHandlingMethodException {
    Answer answer = answerService.getAnswerById(answerId);
    if (null == answer) {
      throw new NoSuchRequestHandlingMethodException("voteUpAnswer", this.getClass());
    }

    return performAnswerVote(answer, SecurityTools.getAuthenticatedUser(), Vote.UP);
  }

  @RolesAllowed({ StandardAuthorities.USER })
  @RequestMapping(value = "/answer/votedown/{answerId}", method = RequestMethod.GET)
  public @ResponseBody Map<String, Integer> voteDownAnswer(@PathVariable final Long answerId) throws NoSuchRequestHandlingMethodException {
    Answer answer = answerService.getAnswerById(answerId);
    if (null == answer) {
      throw new NoSuchRequestHandlingMethodException("voteDownAnswer", this.getClass());
    }

    return performAnswerVote(answer, SecurityTools.getAuthenticatedUser(), Vote.DOWN);
  }

  private Map<String, Integer> performAnswerVote(Answer answer, User user, int type) {
    Vote vote = voteService.findForUser(user, answer);
    return performVote(user, type, vote, answer.getVoteCount(), answer.getId(), Vote.ParentType.ANSWER);
  }

  private Map<String, Integer> performVote(User user, int type, Vote vote, int voteCount, Long parentId, Vote.ParentType parentType) {
    if (null == vote) {
      vote = new Vote();
      voteCount += type;
    }
    else {
      voteCount -= vote.getType();
      if (vote.getType() == type) {
        voteService.deleteById(vote.getId());
        return jsonVoteResponse(voteCount, Vote.NEUTRAL);
      }
      else {
        voteCount = voteCount + type;
      }
    }

    vote
        .type(type)
        .author(user)
        .parentType(parentType)
        .parentId(parentId);
    voteService.store(vote);

    return jsonVoteResponse(voteCount, type);
  }

  private Map<String, Integer> jsonVoteResponse(int voteCount, int type) {
    Map<String, Integer> result = new HashMap<String, Integer>();
    result.put("count", voteCount);
    result.put("type", type);
    return result;
  }
}