package com.zeroturnaround.rebelanswers.validation;

import com.zeroturnaround.rebelanswers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, Object> {
  @Autowired
  private UserService userService;

  @Override
  public void initialize(final EmailUnique constraintAnnotation) {
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    try {
      if (userService.findByEmail((String) value) != null) {
        return false;
      }
    }
    catch (final Exception ignore) {
      // ignore
    }
    return true;
  }

  public UserService getUserService() {
    return userService;
  }
}