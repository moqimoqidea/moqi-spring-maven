package com.moqi.spel;

import com.moqi.validator.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author moqi
 * @date 4/4/22 13:00
 */
@Slf4j
public class SpelTest01 {

    private static final String MESSAGE = "message:{}";

    public static void main(String[] args) {
        exp("'Hello World'");

        exp("'Hello World'.concat('!')");

        bytes();

        length();

        construct();

        booleanTest();

        convertDeclareTypeTest();

        autoGrowTest();
    }

    private static void autoGrowTest() {
        // Turn on:
        // - auto null reference initialization
        // - auto collection growing
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);

        ExpressionParser parser = new SpelExpressionParser(config);

        Expression expression = parser.parseExpression("stringList[3]");

        StringListClass stringListClass = new StringListClass();

        // stringListClass.list will now be a real collection of 4 entries
        // Each entry is a new empty String
        expression.getValue(stringListClass);
        log.info("stringListClass:{}", stringListClass);
    }

    private static void convertDeclareTypeTest() {
        BooleanListClass booleanListClass = new BooleanListClass();
        booleanListClass.booleanList.add(true);

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        // "false" is passed in here as a String. SpEL and the conversion service
        // will recognize that it needs to be a Boolean and convert it accordingly.
        new SpelExpressionParser().parseExpression("booleanList[0]").setValue(context, booleanListClass, "false");

        // b is false
        Boolean b = booleanListClass.booleanList.get(0);
        log.info("b:{}", b);
    }

    private static void booleanTest() {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, Calendar.AUGUST, 9);

        // The constructor arguments are name, birthday, and nationality.
        Person tesla = Person.builder().name("Nikola Tesla").birthday(c.getTime()).nationality("Serbian").build();

        ExpressionParser parser = new SpelExpressionParser();

        // Parse name as an expression
        Expression exp = parser.parseExpression("name");
        String name = (String) exp.getValue(tesla);
        // name == "Nikola Tesla"
        log.info("name:{}", name);

        Expression booleanExp = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = Objects.requireNonNull(booleanExp.getValue(tesla, Boolean.class));
        // result == true
        log.info("result:{}", result);
    }

    private static void construct() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
        String message = exp.getValue(String.class);
        log.info(MESSAGE, message);
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
        log.info(MESSAGE, message);
    }

    private static void exp(String expressionString) {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expressionString);
        String message = (String) exp.getValue();
        log.info(MESSAGE, message);
    }

}

@Data
class BooleanListClass {
    public final List<Boolean> booleanList = new ArrayList<>();
}

@Data
class StringListClass {
    public final List<String> stringList = new ArrayList<>();
}
