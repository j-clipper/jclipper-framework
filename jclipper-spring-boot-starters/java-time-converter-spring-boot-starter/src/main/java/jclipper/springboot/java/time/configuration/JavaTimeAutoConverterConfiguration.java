package jclipper.springboot.java.time.configuration;

import jclipper.springboot.java.time.converter.*;
import jclipper.springboot.java.time.properties.JavaTimePatternProperties;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * 参考
 * <a href="https://gist.github.com/abop/4be6d8b3538c18f720484a783811278c">订制默认的 jackson mapper, 自定义 java 8 time api 中对象的序列化格式</a>
 * <a href="https://www.petrikainulainen.net/programming/spring-framework/spring-from-the-trenches-using-type-converters-with-spring-mvc/"> Using Type Converters With Spring MVC</a>
 *
 * @author wf2311
 * @since 2017/05/12 19:11.
 */
@EnableConfigurationProperties(JavaTimePatternProperties.class)
@Configuration
public class JavaTimeAutoConverterConfiguration implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(JavaTimeAutoConverterConfiguration.class);

    private JavaTimePatternProperties properties;

    public JavaTimeAutoConverterConfiguration(JavaTimePatternProperties properties) {
        this.properties = properties;
        if (log.isInfoEnabled()) {
            log.info("using patterns with properties='{}'", properties);
        }
    }

    /**
     * 订制默认的 jackson mapper, 自定义 java 8 time api 中对象的序列化格式
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(JavaTimeModule javaTimeModule) {
        return builder -> builder.modules(javaTimeModule);
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(properties.getDateTime())));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(properties.dateFormatter()));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(properties.timeFormatter()));
        javaTimeModule.addSerializer(YearMonth.class, new YearMonthSerializer(properties.yearMonthFormatter()));
        javaTimeModule.addSerializer(MonthDay.class, new MonthDaySerializer(properties.monthDayFormatter()));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(properties.dateTimeFormatter()));
        javaTimeModule.addDeserializer(Date.class, new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance, new SimpleDateFormat(properties.getDateTime()), properties.getDateTime()));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(properties.dateFormatter()));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(properties.timeFormatter()));
        javaTimeModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(properties.yearMonthFormatter()));
        javaTimeModule.addDeserializer(MonthDay.class, new MonthDayDeserializer(properties.monthDayFormatter()));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(properties.dateTimeFormatter()));
        return javaTimeModule;
    }

    /**
     * 注册自定义的日期时间转换器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConverter(properties));
        registry.addConverter(new LocalDateConverter(properties.getDate()));
        registry.addConverter(new LocalTimeConverter(properties.getTime()));
        registry.addConverter(new YearMonthConverter(properties.getYearMonth()));
        registry.addConverter(new MonthDayConverter(properties.getMonthDay()));
        registry.addConverter(new LocalDateTimeConverter(properties.getDateTime()));
    }
}
