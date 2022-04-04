package com.moqi.spel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author moqi
 * @date 4/4/22 13:00
 */
@Slf4j
public class SpelTest01 {

    public static void main(String[] args) {
        exp("'Hello World'");

        exp("'Hello World'.concat('!')");

        bytes();

        length();
    }

    private static void length() {
        ExpressionParser parser = new SpelExpressionParser();

        // invokes 'getBytes().length'
        Expression exp = parser.parseExpression("'Hello World'.bytes.length");
        int length = (Integer) Objects.requireNonNull(exp.getValue());
        log.info("length:{}", length);
    }

    private static void bytes() {
        ExpressionParser parser = new SpelExpressionParser();

        // invokes 'getBytes()'
        Expression exp = parser.parseExpression("'Hello World bytes test'.bytes");
        byte[] bytes = (byte[]) exp.getValue();

        String message = new String(Objects.requireNonNull(bytes), StandardCharsets.UTF_8);
        log.info("message:{}", message);
    }

    private static void exp(String expressionString) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expressionString);
        String message = (String) exp.getValue();
        log.info("message:{}", message);
    }

}
