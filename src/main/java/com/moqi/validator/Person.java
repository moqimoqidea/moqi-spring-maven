package com.moqi.validator;

import lombok.Data;

import java.io.Serializable;

/**
 * @author moqi
 * @date 4/4/22 10:10
 */
@Data
public class Person implements Serializable {

    private String name;
    private int age;

}
