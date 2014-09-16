package com.zeroturnaround.rebelanswers.domain;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "content")
  private String content;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User author;
  @Column(name = "created_at")
  private Date created;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "question_id")
  private Question question;
  @Formula("(select sum(v.type) from votes v where v.parent_type = 0 and v.parent_id = id)")
  private Integer voteCount;
  @Transient
  private Integer autenticatedUserVote;
  @Transient
  private List<Comment> comments;

  public Answer() {
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String content) {
    this.content = content;
  }

  public Answer content(final String content) {
    setContent(content);
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(final User author) {
    this.author = author;
  }

  public Answer author(final User author) {
    setAuthor(author);
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Answer created(Date created) {
    setCreated(created);
    return this;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public Answer question(Question question) {
    setQuestion(question);
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

    Answer answer = (Answer) o;

    if (id != null ? !id.equals(answer.id) : answer.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Answer(" + id + ") : " + content;
  }
}
