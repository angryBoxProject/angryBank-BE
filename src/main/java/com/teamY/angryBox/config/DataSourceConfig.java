package com.teamY.angryBox.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:/application.yml")
@MapperScan(basePackages = "com.teamY.angryBox.mapper")
public class DataSourceConfig {

    private final ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() throws Exception{
        DataSource dataSource = new HikariDataSource(hikariConfig());
        log.info("datasource : {}", dataSource.toString());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        log.info("dataSource : " + dataSource.toString());
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:/mybatis-mapper/*Mapper.xml"));
        sqlSessionFactory.setTypeAliasesPackage("com.teamY.angryBox.vo");
        //sqlSessionFactory.setConfiguration(mybatisConfig());
        return sqlSessionFactory.getObject();
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // mybatis와 관련된 설정들
    // appication.properties에 정의되어 있는 mybatis.configuration이 붙은 모든걸 가져와 등록한다.
//    @Bean
//    @ConfigurationProperties(prefix = "mybatis.configuration")
//    public org.apache.ibatis.session.Configuration mybatisConfig(){
//        return new org.apache.ibatis.session.Configuration();
//    }
}

