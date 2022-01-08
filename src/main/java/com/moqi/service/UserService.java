package com.moqi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * https://javakk.com/264.html
 * Spock如何解决传统单元测试开发中的痛点
 *
 * @author moqi
 * On 2021/8/15 14:27
 */
@Service
@Slf4j
public class UserService {

    public int getRandomNumber(int maxValue) {
        return new Random().nextInt(maxValue);
    }

}
