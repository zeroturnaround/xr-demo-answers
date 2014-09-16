package com.zeroturnaround.rebelanswers.mvc.model;

import com.zeroturnaround.rebelanswers.validation.EmailUnique;
import com.zeroturnaround.rebelanswers.validation.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "verifyPassword")
public class RegistrationData {
  @NotEmpty @Email @EmailUnique
  private String email;
  @NotEmpty @Size(max = 255)
  private String name;
  @NotEmpty @Size(min = 6, max = 255)
  private String password;
  @NotEmpty @Size(min = 6, max = 255)
  private String verifyPassword;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getVerifyPassword() {
    return verifyPassword;
  }

  public void setVerifyPassword(String verifyPassword) {
    this.verifyPassword = verifyPassword;
  }
}