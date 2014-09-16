package com.zeroturnaround.rebelanswers.service.impl;

import com.zeroturnaround.rebelanswers.dao.QuestionDao;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DaoQuestionService implements QuestionService {

  private final QuestionDao questionDao;

  public DaoQuestionService(final QuestionDao questionDao) {
    this.questionDao = questionDao;
  }

  @Transactional(readOnly = true)
  public Question getQuestionById(Long id) {
    return questionDao.getQuestionById(id);
  }

  @Transactional(readOnly = true)
  public Question getFullQuestionById(Long id) {
    return questionDao.getFullQuestionById(id);
  }

  @Transactional(readOnly = true)
  public Page<Question> getAllQuestions(Pageable pageable) {
    return questionDao.getAllQuestions(pageable);
  }

  @Transactional(readOnly = true)
  public Page<Question> getQuestionsWithoutAnswers(Pageable pageable) {
    return questionDao.getQuestionsWithoutAnswers(pageable);
  }

  @Transactional(readOnly = true)
  public Page<Question> getUnansweredQuestions(Pageable pageable) {
    return questionDao.getUnansweredQuestions(pageable);
  }

  @Transactional(readOnly = true)
  public Page<Question> getUnansweredQuestionsWithoutAnswers(Pageable pageable) {
    return questionDao.getUnansweredQuestionsWithoutAnswers(pageable);
  }

  @Transactional(readOnly = true)
  public List<Question> getQuestionsForAuthor(User user) {
    return questionDao.getQuestionsForAuthor(user);
  }

  @Transactional(readOnly = true)
  public Page<Question> searchQuestions(String search, Pageable pageable) {
    return questionDao.searchQuestions(search, pageable);
  }

  @Transactional(readOnly = false)
  public boolean store(final Question question) {
    if (null == question) {
      return false;
    }

    questionDao.persistOrMerge(question);
    return true;
  }
}
