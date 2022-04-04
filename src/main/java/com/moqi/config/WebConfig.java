package com.moqi.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

/**
 * https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-config-conversion=
 *
 * @author moqi
 * @date 4/4/22 12:19
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * moqiFIXME: 4/4/22 12:43: not work
     * 注册全局日期格式化器
     */
    @Override
    public void addFormatters(@NotNull FormatterRegistry formatterRegistry) {
        // Register JSR-310 date conversion with a specific global format
        DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
        dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy MM dd hh : mm : ss"));
        dateTimeFormatterRegistrar.registerFormatters(formatterRegistry);

        // Register date conversion with a specific global format
        DateFormatterRegistrar dateFormatterRegistrar = new DateFormatterRegistrar();
        dateFormatterRegistrar.setFormatter(new DateFormatter("yyyy MM dd"));
        dateFormatterRegistrar.registerFormatters(formatterRegistry);
    }
}
