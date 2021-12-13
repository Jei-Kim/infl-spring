package com.hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
Configuration.class))// 기존에 AppConfig,TestConfig 설정 정보 실행 방지용 
public class AutoAppConfig {

}
// 기존의 AppConfig와는 다르게 @Bean으로 등록한 클래스가 하나도 없다