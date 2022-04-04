package com.moqi.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author moqi
 * @date 4/4/22 10:59
 */
@Slf4j
public class ConverterTest01 {

    public static void main(String[] args) {
        uuidTest();

        arrayTest();

        arrayToListTest();
    }

    private static void arrayToListTest() {
        int[] intArray = {1, 2, 3, 4, 5};
        DefaultConversionService defaultConversionService = new DefaultConversionService();

        Object convert = defaultConversionService.convert(intArray,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(int.class)));

        //noinspection unchecked
        List<Integer> list = (List<Integer>) convert;
        log.info("list:{}", list);
    }

    private static void arrayTest() {
        String commaSeparatedString = "1,2,3,4,5";

        DefaultConversionService defaultConversionService = new DefaultConversionService();
        String[] stringArray = defaultConversionService.convert(commaSeparatedString, String[].class);
        log.info("stringArray:{}", Arrays.deepToString(stringArray));

        Integer[] intArray = defaultConversionService.convert(commaSeparatedString, Integer[].class);
        log.info("intArray:{}", Arrays.deepToString(intArray));
    }

    private static void uuidTest() {
        String uuidString = UUID.randomUUID().toString();
        log.info("uuidString:{}", uuidString);

        selfConverter(uuidString);

        springConvert(uuidString);
    }

    private static void springConvert(String uuidString) {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        UUID uuid = defaultConversionService.convert(uuidString, UUID.class);
        log.info("uuid:{}", uuid);
    }

    private static void selfConverter(String uuidString) {
        Converter<String, UUID> converter = source -> (StringUtils.hasText(source) ? UUID.fromString(source.trim()) : null);

        UUID uuid = converter.convert(uuidString);
        log.info("uuid:{}", uuid);
    }

}
