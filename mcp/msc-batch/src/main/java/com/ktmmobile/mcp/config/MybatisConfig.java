package com.ktmmobile.mcp.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MybatisConfig {

	@Autowired
	@Qualifier(value="bootoradbDataSource")
	private DataSource bootoradbDataSource;

	@Autowired
	@Qualifier(value="booteventdbDataSource")
	private DataSource booteventdbDataSource;

    @Bean
    @Primary
    public SqlSessionFactory bootOraSqlSessionFactoryBean() throws java.lang.Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(bootoradbDataSource);
        /* 맵퍼 xml 파일 경로 설정 */
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mcp/mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        /* alias 설정 com.package..entity.Board -> resultType"Board" */
        //sqlSessionFactoryBean.setTypeAliasesPackage("com.gglee.sample.*.entity");

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        /* 실제DB컬럼명 스네이크 표기법 = 카멜케이스 표기법 맵핑 */
        configuration.setMapUnderscoreToCamelCase(true);
        /* jdbcTypeForNull = NULL 세팅 */
        configuration.setJdbcTypeForNull(JdbcType.NULL);

        return sqlSessionFactory;
    }

    @Bean
    public SqlSessionFactory booteventSqlSessionFactoryBean() throws java.lang.Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(booteventdbDataSource);
        /* 맵퍼 xml 파일 경로 설정 */
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mcp/eventMapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        /* alias 설정 com.package..entity.Board -> resultType"Board" */
        //sqlSessionFactoryBean.setTypeAliasesPackage("com.gglee.sample.*.entity");

        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();

        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        /* 실제DB컬럼명 스네이크 표기법 = 카멜케이스 표기법 맵핑 */
        configuration.setMapUnderscoreToCamelCase(true);
        /* jdbcTypeForNull = NULL 세팅 */
        configuration.setJdbcTypeForNull(JdbcType.NULL);

        return sqlSessionFactory;
    }

    @Bean
    @Primary
    public SqlSessionTemplate bootoraSqlSession() throws java.lang.Exception {
        return new SqlSessionTemplate(bootOraSqlSessionFactoryBean());
    }

    @Bean
    public SqlSessionTemplate booteventSqlSession() throws java.lang.Exception {
        return new SqlSessionTemplate(booteventSqlSessionFactoryBean());
    }
}
