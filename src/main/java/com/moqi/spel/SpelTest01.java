package com.moqi.spel;

import com.moqi.validator.Person;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
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

        spelCompilerModeTest();

        listMapTest();

        arrayTest();

        methodTest();

        compliexBooleanTest();

        classTest();

        variableTest();

        registerMethodTest();

        evilsTest();
    }

    private static void evilsTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        Person personWithName = Person.builder().name("Nikola Tesla").build();
        String name = parser.parseExpression("name?:'Elvis Presley'")
                .getValue(context, personWithName, String.class);
        // Nikola Tesla
        log.info("name:{}", name);

        Person personNoName = Person.builder().build();
        String defaultName = parser.parseExpression("name?:'Elvis Presley'")
                .getValue(context, personNoName, String.class);
        // Elvis Presley
        log.info("defaultName:{}", defaultName);
    }

    private static void registerMethodTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        try {
            context.setVariable("isMan", Person.class.getDeclaredMethod("isMan", String.class));
            boolean isMan = Objects.requireNonNull(
                    parser.parseExpression("#isMan('tom smith')").getValue(context, boolean.class));
            log.info("isMan:{}", isMan);

            boolean isNotMan = Objects.requireNonNull(
                    parser.parseExpression("#isMan('jerry smith')").getValue(context, boolean.class));
            log.info("isNotMan:{}", isNotMan);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private static void variableTest() {
        // create an array of integers
        List<Integer> primes = List.of(2, 3, 5, 7, 11, 13, 17);

        // create parser and set variable 'primes' as the array of integers
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        context.setVariable("primes", primes);

        // all prime numbers > 10 from the list (using selection ?{...})
        // evaluates to [11, 13, 17]
        //noinspection unchecked
        List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
                "#primes.?[#this>10]").getValue(context);
        log.info("primesGreaterThanTen:{}", primesGreaterThanTen);
    }

    private static void classTest() {
        ExpressionParser parser = new SpelExpressionParser();
        Class<?> dateClass = parser.parseExpression("T(java.util.Date)").getValue(Class.class);
        log.info("dateClass:{}", dateClass);

        Class<?> stringClass = parser.parseExpression("T(String)").getValue(Class.class);
        log.info("stringClass:{}", stringClass);

        boolean trueValue = Objects.requireNonNull(parser.parseExpression(
                        "T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR")
                .getValue(Boolean.class));
        log.info("trueValue:{}", trueValue);

        Integer maxValue = Objects.requireNonNull(
                parser.parseExpression("T(java.lang.Math).max(10, 20)").getValue(int.class));
        log.info("maxValue:{}", maxValue);

        boolean isMan = Objects.requireNonNull(
                parser.parseExpression("T(com.moqi.validator.Person).isMan('tom smith')").getValue(boolean.class));
        log.info("isMan:{}", isMan);
    }

    private static void compliexBooleanTest() {
        ExpressionParser parser = new SpelExpressionParser();
        // evaluates to false
        boolean falseValue = Objects.requireNonNull(
                parser.parseExpression("'xyz' instanceof T(Integer)").getValue(Boolean.class));
        log.info("falseValue:{}", falseValue);

        // evaluates to true
        boolean trueValue = Objects.requireNonNull(
                parser.parseExpression("'5.00' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class));
        log.info("trueValue:{}", trueValue);

        // evaluates to false
        boolean falseValue2 = Objects.requireNonNull(parser.parseExpression(
                "'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class));
        log.info("falseValue2:{}", falseValue2);
    }

    private static void methodTest() {
        ExpressionParser parser = new SpelExpressionParser();
        // string literal, evaluates to "bc"
        String bc = parser.parseExpression("'abc'.substring(1, 3)").getValue(String.class);
        log.info("bc:{}", bc);
    }

    private static void arrayTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        int[] numbers1 = (int[]) parser.parseExpression("new int[4]").getValue(context);
        log.info("numbers1:{}", numbers1);

        // Array with initializer
        int[] numbers2 = (int[]) parser.parseExpression("new int[]{1,2,3}").getValue(context);
        log.info("numbers2:{}", numbers2);

        // Multi dimensional array
        int[][] numbers3 = (int[][]) parser.parseExpression("new int[4][5]").getValue(context);
        log.info("numbers3:{}", Arrays.deepToString(numbers3));
    }

    private static void listMapTest() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();

        // evaluates to a Java list containing the four numbers
        List<?> numbers = (List<?>) parser.parseExpression("{1,2,3,4}").getValue(context);
        log.info("numbers:{}", numbers);

        List<?> listOfLists = (List<?>) parser.parseExpression("{{'a','b'},{'x','y'}}").getValue(context);
        log.info("listOfLists:{}", listOfLists);

        // evaluates to a Java map containing the two entries
        Map<?, ?> inventorInfo = (Map<?, ?>) parser.parseExpression("{name:'Nikola',dob:'10-July-1856'}").getValue(context);
        log.info("inventorInfo:{}", inventorInfo);

        Map<?, ?> mapOfMaps = (Map<?, ?>) parser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue(context);
        log.info("mapOfMaps:{}", mapOfMaps);
    }

    private static void spelCompilerModeTest() {
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
                SpelTest01.class.getClassLoader());
        SpelExpressionParser parser = new SpelExpressionParser(config);
        Expression expr = parser.parseExpression("'payload'");
        String payload = (String)expr.getValue(expr);
        log.info("payload:{}", payload);
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
