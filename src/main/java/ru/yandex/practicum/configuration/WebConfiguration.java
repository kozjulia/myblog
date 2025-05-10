package ru.yandex.practicum.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"ru.yandex.practicum.configuration"})
public class WebConfiguration {

}
