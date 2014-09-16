package com.zeroturnaround.rebelanswers.mvc.controller;

import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.service.AnswerService;
import com.zeroturnaround.rebelanswers.service.QuestionService;
import com.zeroturnaround.rebelanswers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

@Controller
public class ProfileController {

  private final UserService userService;
  private final QuestionService questionService;
  private final AnswerService answerService;

  @Autowired
  public ProfileController(UserService userService, final QuestionService questionService, final AnswerService answerService) {
    this.userService = userService;
    this.questionService = questionService;
    this.answerService = answerService;
  }

  protected ProfileController() {
    // CGLib AOP needs a protected default constructor
    this.userService = null;
    this.questionService = null;
    this.answerService = null;
  }

  @RequestMapping(value = "/profile/{userId}/{userName}/questions", method = RequestMethod.GET)
  public ModelAndView showProfileQuestions(@PathVariable final Long userId, @PathVariable final String userName) throws NoSuchRequestHandlingMethodException {
    final ModelAndView mav = new ModelAndView("profile/questions");

    User user = userService.findById(userId);
    if (null == user) {
      throw new NoSuchRequestHandlingMethodException("showProfileQuestions", this.getClass());
    }

    mav.addObject(user);
    mav.addObject("questions", questionService.getQuestionsForAuthor(user));

    return mav;
  }

  @RequestMapping(value = "/profile/{userId}/{userName}/answers", method = RequestMethod.GET)
  public ModelAndView showProfileAnswers(@PathVariable final Long userId, @PathVariable final String userName) throws NoSuchRequestHandlingMethodException {
    final ModelAndView mav = new ModelAndView("profile/answers");

    User user = userService.findById(userId);
    if (null == user) {
      throw new NoSuchRequestHandlingMethodException("showProfileAnswers", this.getClass());
    }

    mav.addObject(user);
    mav.addObject("answers", answerService.getAnswersForAuthor(user));

    return mav;
  }
}