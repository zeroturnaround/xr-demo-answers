package com.zeroturnaround.rebelanswers.dao.jpa;

import com.zeroturnaround.rebelanswers.dao.CommentDao;
import com.zeroturnaround.rebelanswers.dao.QuestionDao;
import com.zeroturnaround.rebelanswers.dao.VoteDao;
import com.zeroturnaround.rebelanswers.domain.Answer;
import com.zeroturnaround.rebelanswers.domain.Question;
import com.zeroturnaround.rebelanswers.domain.User;
import com.zeroturnaround.rebelanswers.domain.Vote;
import com.zeroturnaround.rebelanswers.security.SecurityTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

  private final DaoTools daoTools;
  private final VoteDao voteDao;
  private final CommentDao commentDao;

  public QuestionDaoImpl(final DaoTools daoTools, final VoteDao voteDao, final CommentDao commentDao) {
    if (null == daoTools) throw new IllegalArgumentException("daoTools can't be null");
    if (null == voteDao) throw new IllegalArgumentException("voteDao can't be null");
    if (null == commentDao) throw new IllegalArgumentException("commentDao can't be null");
    this.daoTools = daoTools;
    this.voteDao = voteDao;
    this.commentDao = commentDao;
  }

  public Question getQuestionById(Long id) {
    return daoTools.findById(Question.class, id);
  }

  public Question getFullQuestionById(Long id) {
    Question question = daoTools.findById(Question.class, id);

    Vote question_vote = voteDao.findForUser(SecurityTools.getAuthenticatedUser(), question);
    if (question_vote != null) {
      question.setAutenticatedUserVote(question_vote.getType());
    }

    question.setComments(commentDao.getCommentsForQuestion(question));

    if (question.getAnswers() != null) {
      for (Answer answer : question.getAnswers()) {
        Vote answer_vote = voteDao.findForUser(SecurityTools.getAuthenticatedUser(), answer);
        if (answer_vote != null) {
          answer.setAutenticatedUserVote(answer_vote.getType());
        }

        answer.setComments(commentDao.getCommentsForAnswer(answer));
      }
    }

    return question;
  }

  public Page<Question> getAllQuestions(Pageable pageable) {
    List<Question> content = daoTools.getAllEntities(Question.class, "created", DaoTools.SortOrder.DESC, pageable.getOffset(), pageable.getPageSize());
    long total = daoTools.countEntities(Question.class);
    return new PageImpl<Question>(content, pageable, total);
  }

  public Page<Question> getQuestionsWithoutAnswers(Pageable pageable) {
    String filter = "where size(answers) = 0";
    List<Question> content = daoTools.getFilteredEntities(Question.class, "created", DaoTools.SortOrder.DESC, filter, pageable.getOffset(), pageable.getPageSize());
    long total = daoTools.countFilteredEntities(Question.class, filter);
    return new PageImpl<Question>(content, pageable, total);
  }

  public Page<Question> getUnansweredQuestions(Pageable pageable) {
    String filter = "where acceptedAnswer is null";
    List<Question> content = daoTools.getFilteredEntities(Question.class, "created", DaoTools.SortOrder.DESC, filter, pageable.getOffset(), pageable.getPageSize());
    long total = daoTools.countFilteredEntities(Question.class, filter);
    return new PageImpl<Question>(content, pageable, total);
  }

  public Page<Question> getUnansweredQuestionsWithoutAnswers(Pageable pageable) {
    String filter = "where acceptedAnswer is null and size(answers) = 0";
    List<Question> content = daoTools.getFilteredEntities(Question.class, "created", DaoTools.SortOrder.DESC, filter, pageable.getOffset(), pageable.getPageSize());
    long total = daoTools.countFilteredEntities(Question.class, filter);
    return new PageImpl<Question>(content, pageable, total);
  }

  public Page<Question> searchQuestions(String search, Pageable pageable) {
    long total = daoTools.countSearchByAttribute(Question.class, "title", search);
    List<Question> content = daoTools.searchByAttribute(Question.class, "created", DaoTools.SortOrder.DESC, "title", search, pageable.getOffset(), pageable.getPageSize());
    return new PageImpl<Question>(content, pageable, total);
  }

  public List<Question> getQuestionsForAuthor(User user) {
    return daoTools.findByAttributes(Question.class, "created", DaoTools.SortOrder.DESC, "author", user);
  }

  public Question persistOrMerge(final Question question) {
    if (null == question) throw new IllegalArgumentException("question can't be null");

    if (question.getId() == null) {
      return daoTools.persist(Question.class, question);
    }
    else {
      return daoTools.merge(Question.class, question);
    }
  }
}
