package com.clover.moodiary.configuration;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.clover.moodiary", annotationClass = Mapper.class)
public class MybatisConfiguration {
}
