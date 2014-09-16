package com.zeroturnaround.rebelanswers.validation;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize(final FieldMatch constraintAnnotation) {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid(final Object value, final ConstraintValidatorContext context) {
    try {
      final Object first = BeanUtils.getProperty(value, firstFieldName);
      final Object second = BeanUtils.getProperty(value, secondFieldName);

      return first == null && second == null || first != null && first.equals(second);
    }
    catch (final Exception ignore) {
      // ignore
    }
    return true;
  }
}