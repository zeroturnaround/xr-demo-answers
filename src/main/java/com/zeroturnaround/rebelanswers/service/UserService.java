package com.zeroturnaround.rebelanswers.service;

import com.zeroturnaround.rebelanswers.domain.User;

public interface UserService {

  public User findByUsername(String username);

  public User findByFacebookId(String facebookId);

  public User findByEmail(String email);

  public User findById(Long userId);

  public boolean store(User user);
}