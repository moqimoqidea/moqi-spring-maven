package com.moqi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moqi
 * On 2021/8/15 23:05
 */
@RestController
public class HelloController {

    @GetMapping("/hello/{id}")
    String person(@PathVariable Long id) {
        return String.valueOf(id);
    }

}
