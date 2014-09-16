package com.zeroturnaround.rebelanswers.dao.jpa;

import com.zeroturnaround.rebelanswers.dao.UserDao;
import com.zeroturnaround.rebelanswers.domain.User;

import java.util.List;

public class UserDaoImpl implements UserDao {

  private final DaoTools daoTools;

  public UserDaoImpl(final DaoTools daoTools) {
    if (null == daoTools) throw new IllegalArgumentException("daoTools can't be null");
    this.daoTools = daoTools;
  }

  public User findByUsername(final String username) {
    final List<User> list = daoTools.findByPossibleAttributes(User.class, new String[] { "email", "facebookId" }, username);
    if (list == null || list.isEmpty()) return null;
    return list.get(0);
  }

  public User findByFacebookId(final String facebookId) {
    final List<User> list = daoTools.findByAttribute(User.class, "facebookId", facebookId);
    if (list == null || list.isEmpty()) return null;
    return list.get(0);
  }

  public User findByEmail(final String email) {
    final List<User> list = daoTools.findByAttribute(User.class, "email", email);
    if (list == null || list.isEmpty()) return null;
    return list.get(0);
  }

  public User findById(Long userId) {
    return daoTools.findById(User.class, userId);
  }

  public User persistOrMerge(final User user) {
    if (null == user) throw new IllegalArgumentException("user can't be null");

    if (user.getId() == null) {
      return daoTools.persist(User.class, user);
    }
    else {
      return daoTools.merge(User.class, user);
    }
  }
}
