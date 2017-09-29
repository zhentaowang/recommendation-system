package com.adatafun.recommendation.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.Driver;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatisConfig.java
 * Copyright(C) 2017 杭州风数科技有限公司
 * Created by wzt on 05/09/2017.
 */
@Configuration
@EnableTransactionManagement
@Import({SpringConfiguration.class})
public class MyBatisConfig {

    @Bean(name = "resourceLoader")
    public ResourceLoader resourceLoader(){
        return new DefaultResourceLoader();
    }

    @Bean(name = "resourcePatternResolver")
    public ResourcePatternResolver resourcePatternResolver(){
        return new PathMatchingResourcePatternResolver();
    }

    @Profile("production")
    @Bean(name = "dataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(Environment environment) throws Exception{
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName("masterDataSource");
        druidDataSource.setUsername(environment.getProperty("spring.datasource.username"));
        druidDataSource.setPassword(environment.getProperty("spring.datasource.password"));
        druidDataSource.setUrl(environment.getProperty("spring.datasource.url"));
        druidSettings(druidDataSource);
        return druidDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.adatafun.recommendation.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource,ResourceLoader resourceLoader,ResourcePatternResolver resourcePatternResolver) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:conf/mybatis/mapper/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:mybatis.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.adatafun.recommendation.model");
        return sqlSessionFactoryBean;
    }

    private void druidSettings(DruidDataSource druidDataSource) throws Exception{
        druidDataSource.setMaxActive(40);
        druidDataSource.setInitialSize(3);
        druidDataSource.setUseUnfairLock(true);
        druidDataSource.setMinIdle(10);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setPoolPreparedStatements(false);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(6000);
        druidDataSource.setMinEvictableIdleTimeMillis(30000*4);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(180);
        druidDataSource.setLogAbandoned(true);
        druidDataSource.setFilters("stat");
        druidDataSource.setTimeBetweenLogStatsMillis(60000*60); //每10分钟自动将监控的数据存储到日志中
        druidDataSource.setDriver(new Driver());
    }
}
