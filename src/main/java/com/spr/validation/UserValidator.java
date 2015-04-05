package com.spr.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.spr.model.User;

@Component
public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;


        ValidationUtils.rejectIfEmpty(errors, "name", "user.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "age", "user.age.empty");

        int age;
        try {
            age = user.getAge();
        } catch (NullPointerException e) {
            age = 0;
        }

        if (age <= 0)
            errors.rejectValue("age", "user.age.negativeOrZero");

    }

}
