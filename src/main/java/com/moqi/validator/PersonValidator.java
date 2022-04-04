package com.moqi.validator;

import com.moqi.bean.validator.Person;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author moqi
 * @date 4/4/22 10:12
 */
public class PersonValidator implements Validator {

    private static final int MAX_AGE = 110;
    private static final int MIN_AGE = 0;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
        Person p = (Person) obj;

        if (p.getAge() < MIN_AGE) {
            e.rejectValue("age", "age.negative");
        } else if (p.getAge() > MAX_AGE) {
            e.rejectValue("age", "-1", "age value is too high!");
        }
    }

}
