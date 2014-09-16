package com.zeroturnaround.rebelanswers.dao;

import com.zeroturnaround.rebelanswers.domain.User;

public interface UserDao {

  public User findByUsername(String username);

  public User findByFacebookId(String facebookId);

  public User findByEmail(String email);

  public User persistOrMerge(User user);

  public User findById(Long userId);
}
