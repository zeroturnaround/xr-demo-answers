package com.zeroturnaround.rebelanswers.dao;

import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionDao {

  public Question getQuestionById(Long id);

  public Question getFullQuestionById(Long id);

  public Page<Question> getAllQuestions(Pageable pageable);

  public Page<Question> getQuestionsWithoutAnswers(Pageable pageable);

  public Page<Question> getUnansweredQuestions(Pageable pageable);

  public Page<Question> getUnansweredQuestionsWithoutAnswers(Pageable pageable);

  public List<Question> getQuestionsForAuthor(User user);

  public Page<Question> searchQuestions(String search, Pageable pageable);

  public Question persistOrMerge(Question question);
}
