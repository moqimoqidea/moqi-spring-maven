package com.moqi.validator;

import com.moqi.bean.validator.Company;
import com.moqi.bean.validator.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/**
 * 测试 BeanWrapper
 *
 * @author moqi
 * @date 4/4/22 10:21
 */
@Slf4j
public class BeanWrapperTest01 {

    public static void main(String[] args) {
        BeanWrapper company = new BeanWrapperImpl(new Company());
        // setting the company name
        company.setPropertyValue("name", "Some Company Inc.");
        // ... can also be done like this:
        PropertyValue value = new PropertyValue("name", "Some Company Inc.");
        company.setPropertyValue(value);

        // ok, let's create the director and tie it to the company:
        BeanWrapper employee = new BeanWrapperImpl(new Employee());
        employee.setPropertyValue("name", "Jim");
        employee.setPropertyValue(new PropertyValue("salary", "10000.1234"));
        company.setPropertyValue("managingDirector", employee.getWrappedInstance());

        // retrieving the salary of the managingDirector through the company
        Float salary = (Float) company.getPropertyValue("managingDirector.salary");

        log.info("salary:{}", salary);
        log.info("employee:{}", employee.getWrappedInstance());
        log.info("company:{}", company.getWrappedInstance());
    }

}