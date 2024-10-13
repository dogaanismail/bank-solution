package com.banksolution.account.query.configuration;

import com.banksolution.account.query.mappers.AccountBalanceMapper;
import com.banksolution.account.query.mappers.AccountMapper;
import com.banksolution.account.query.mappers.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private final DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    @Bean
    public MapperFactoryBean<AccountBalanceMapper> accountBalanceMapperMapperFactoryBean(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<AccountBalanceMapper> factoryBean = new MapperFactoryBean<>(AccountBalanceMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<AccountMapper> accountMapperMapperFactoryBean(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<AccountMapper> factoryBean = new MapperFactoryBean<>(AccountMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TransactionMapper> transactionMapperMapperFactoryBean(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TransactionMapper> factoryBean = new MapperFactoryBean<>(TransactionMapper.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
