package com.moqi.validator;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author moqi
 * @date 4/4/22 10:10
 */
@Data
@Builder
public class Person implements Serializable {

    private String name;
    private int age;

    /**
     * moqiFIXME: 4/4/22 12:43: not work
     */
    @DateTimeFormat(pattern = "yyyy MM dd")
    private Date birthday;

}
