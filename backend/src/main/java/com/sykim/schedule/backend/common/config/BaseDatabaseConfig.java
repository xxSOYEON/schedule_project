package com.sykim.schedule.backend.common.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.sykim.schedule.backend.common.enums.DatabaseDriver;
import com.sykim.schedule.backend.common.utils.StringAESEncryptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@MapperScan(basePackages = "com.sykim.schedule.backend.mapper")
public class BaseDatabaseConfig {

    @Value("${password.encryption.key}")
    private String encryptorKey;
    
    @Autowired
	private BaseDatabaseSetting _setting;

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception {
        
        DatabaseDriver driver = DatabaseDriver.from(_setting.getDriverClassName());

        if (driver == DatabaseDriver.H2) {
            return h2DataSource();
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(_setting.getDriverClassName());
        hikariConfig.setJdbcUrl(_setting.getUrl());
        hikariConfig.setUsername(_setting.getUsername());
        hikariConfig.setPassword(_setting.getPassword());

        if(_setting.isEncryption()){
            hikariConfig.setPassword(StringAESEncryptor.decrypt(_setting.getPassword(), encryptorKey));
        }else{
            hikariConfig.setPassword(_setting.getPassword());
        }

        hikariConfig.setMaximumPoolSize(_setting.getHikari().getMaximumPoolSize());
        hikariConfig.setMinimumIdle(_setting.getHikari().getMinimumIdle());
        hikariConfig.setConnectionTimeout(_setting.getHikari().getConnectionTimeout());
        hikariConfig.setIdleTimeout(_setting.getHikari().getIdleTimeout());
        hikariConfig.setMaxLifetime(_setting.getHikari().getMaxLifetime());
        hikariConfig.setPoolName("collection-pool");
        
        return new HikariDataSource(hikariConfig);
    }

    private DataSource h2DataSource() {
        return DataSourceBuilder.create()
            .url(_setting.getUrl())
            .driverClassName(_setting.getDriverClassName())
            .username(_setting.getUsername())
            .password(_setting.getPassword())
            .build();
    }

    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }

    @Primary
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
