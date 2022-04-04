package com.moqi.validator;

import com.moqi.bean.validator.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

/**
 * @author moqi
 * @date 4/4/22 12:51
 */
@Slf4j
public class DataBinderTest01 {

    public static void main(String[] args) {
        Person person = Person.builder().name("tom").age(999).build();
        DataBinder binder = new DataBinder(person);
        binder.setValidator(new PersonValidator());

        // bind to the target object
        // binder.bind(propertyValues);

        // validate the target object
        binder.validate();

        // get BindingResult that includes any validation errors
        BindingResult results = binder.getBindingResult();
        log.info("results:{}", results);
    }
}
