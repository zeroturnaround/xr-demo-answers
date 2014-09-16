package com.zeroturnaround.rebelanswers.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question implements Serializable {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "title")
  private String title;
  @Column(name = "content")
  private String content;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User author;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Answer.class, mappedBy = "question")
  private List<Answer> answers;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "answer_id")
  private Answer acceptedAnswer;
  @Column(name = "created_at")
  private Date created;
  @Formula("(select sum(v.type) from votes v where v.parent_type = 1 and v.parent_id = id)")
  private Integer voteCount;
  @Transient
  private Integer autenticatedUserVote;
  @Transient
  private List<Comment> comments;

  public Question() {
    setAnswers(new ArrayList<Answer>(0));
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Question id(final Long id) {
    this.setId(id);
    return this;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public Question title(final String title) {
    setTitle(title);
    return this;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public Question content(final String content) {
    setContent(content);
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(final User author) {
    this.author = author;
  }

  public Question author(final User author) {
    setAuthor(author);
    return this;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(final List<Answer> answers) {
    this.answers = new ArrayList<Answer>(answers);
  }

  public Question answers(final List<Answer> answerList) {
    setAnswers(answerList);
    return this;
  }

  public Answer getAcceptedAnswer() {
    return acceptedAnswer;
  }

  public void setAcceptedAnswer(final Answer acceptedAnswer) {
    this.acceptedAnswer = acceptedAnswer;
  }

  public Question acceptedAnswer(final Answer acceptedAnswer) {
    setAcceptedAnswer(acceptedAnswer);
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Question created(Date created) {
    setCreated(created);
    return this;
  }

  public Integer getVoteCount() {
    if (null == voteCount) {
      return 0;
    }
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }

  public Integer getAutenticatedUserVote() {
    return autenticatedUserVote;
  }

  public void setAutenticatedUserVote(Integer autenticatedUserVote) {
    this.autenticatedUserVote = autenticatedUserVote;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Question question = (Question) o;

    if (id != null ? !id.equals(question.id) : question.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Question(" + getId() + ") : " + (getAnswers() == null ? "0" : getAnswers().size()) + " answers: '" + getContent() + "'";
  }
}
