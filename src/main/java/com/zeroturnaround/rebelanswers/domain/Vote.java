package com.zeroturnaround.rebelanswers.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote implements Serializable {

  public final static int DOWN = -1;
  public final static int NEUTRAL = 0;
  public final static int UP = 1;
  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "type")
  private int type;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User author;
  @Column(name = "created_at")
  private Date created;
  @Column(name = "parent_type")
  private ParentType parentType;
  @Column(name = "parent_id")
  private Long parentId;

  public Vote() {
    type = NEUTRAL;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Vote type(int type) {
    setType(type);
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(final User author) {
    this.author = author;
  }

  public Vote author(final User author) {
    setAuthor(author);
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Vote created(Date created) {
    setCreated(created);
    return this;
  }

  public ParentType getParentType() {
    return parentType;
  }

  public void setParentType(ParentType parentType) {
    this.parentType = parentType;
  }

  public Vote parentType(ParentType parentType) {
    setParentType(parentType);
    return this;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Vote parentId(Long parentId) {
    setParentId(parentId);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Vote vote = (Vote) o;

    if (id != null ? !id.equals(vote.id) : vote.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Vote(" + id + ") : " + type + ", " + parentType;
  }

  public enum ParentType {ANSWER, QUESTION}
}
