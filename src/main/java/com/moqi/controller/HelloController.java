package com.moqi.controller;

import com.moqi.service.UserService;
import com.moqi.bean.validator.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author moqi
 * On 2021/8/15 23:05
 */
@Slf4j
@RestController
public class HelloController {

    private final UserService userService;

    public HelloController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello/{id}")
    String person(@PathVariable Long id) {
        return id + ", randomNumber = " + userService.getRandomNumber(1000);
    }

    @GetMapping("/random_person")
    String randomPerson() {
        Date birthday = new Date();
        log.info("birthday:{}", birthday);
        return Person.builder().birthday(birthday).name("moqi").build().toString();
    }

}
