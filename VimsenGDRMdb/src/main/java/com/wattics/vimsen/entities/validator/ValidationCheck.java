package com.wattics.vimsen.entities.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.wattics.vimsen.GDRMdatamanager.NoValidDataException;

public class ValidationCheck {

  public static boolean isObjectValid(Object object) {
    if (object == null) {
      return false;
    }
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Set<ConstraintViolation<Object>> prViolations = validator.validate(object);

    return (!(prViolations.size() > 0));
  }

  public static Object returnValidObject(Object object) {
    Object validObject = null;
    if (object != null && isObjectValid(object)) {
      validObject = object;
    }
    return validObject;
  }

  public static List<Object> returnValidListObject(List<?> objects) throws NoValidDataException {
    List<Object> validObject = new ArrayList<>();
    for (Object object : objects) {
      if (isObjectValid(object)) {
        validObject.add(object);
      }
    }
    if (validObject.size() > 0) {
      return validObject;
    }

    throw new NoValidDataException();
  }
}
