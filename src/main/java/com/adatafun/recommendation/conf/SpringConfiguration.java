/*
 * Copyright (c) 2017. Hangzhou FenShu Tech Co., Ltd. All rights reserved.
 * Created by wangzhentao@iairportcloud.com on 2016/09/02
 */

package com.adatafun.recommendation.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan("com.adatafun.recommendation")
public class SpringConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

