package com.zeroturnaround.rebelanswers.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

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
  @Column(name = "parent_type")
  private ParentType parentType;
  @Column(name = "parent_id")
  private Long parentId;

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

  public Comment content(final String content) {
    setContent(content);
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(final User author) {
    this.author = author;
  }

  public Comment author(final User author) {
    setAuthor(author);
    return this;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Comment created(Date created) {
    setCreated(created);
    return this;
  }

  public ParentType getParentType() {
    return parentType;
  }

  public void setParentType(ParentType parentType) {
    this.parentType = parentType;
  }

  public Comment parentType(ParentType parentType) {
    setParentType(parentType);
    return this;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Comment parentId(Long parentId) {
    setParentId(parentId);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Comment comment = (Comment) o;

    if (id != null ? !id.equals(comment.id) : comment.id != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "Comment(" + id + ") : " + parentId + ", " + parentType;
  }

  public enum ParentType {ANSWER, QUESTION}
}
