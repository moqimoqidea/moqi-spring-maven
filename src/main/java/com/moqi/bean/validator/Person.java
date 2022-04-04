package com.moqi.bean.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author moqi
 * @date 4/4/22 10:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private String name;
    private int age;

    /**
     * moqiFIXME: 4/4/22 12:43: not work
     */
    @DateTimeFormat(pattern = "yyyy MM dd")
    private Date birthday;

    private String nationality;

    public Person(String name) {
        this.name = name;
    }

    /**
     * for test spel
     */
    public static boolean isMan(String name) {
        return name.startsWith("tom");
    }

}
