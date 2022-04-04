package com.moqi.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author moqi
 * @date 4/4/22 10:59
 */
@Slf4j
public class ConverterTest01 {

    public static void main(String[] args) {
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
