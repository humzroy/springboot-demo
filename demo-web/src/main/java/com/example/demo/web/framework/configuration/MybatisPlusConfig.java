package com.example.demo.web.framework.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.example.demo.common.datasource.DataSourceConstant;
import com.example.demo.common.datasource.DataSourceContextHolder;
import com.example.demo.web.framework.configuration.ds.transcation.MultiDataSourceTransactionFactory;
import com.example.demo.web.framework.datasource.DynamicDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhengzhen
 * @date 2019/7/30 17:26
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.example.demo.dao.mapper*")
public class MybatisPlusConfig {

    @Qualifier("webApplicationContext")
    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired
    private MybatisProperties properties;

    /**
     * 分页插件，自动识别数据库类型
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDatasource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setValidationQuery("SELECT 1 from DUAL");
        return dataSource;
    }

    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDatasource() {
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        dataSource.setValidationQuery("SELECT 1 from DUAL");
        return dataSource;
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean(name = "multipleDataSource")
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource master, @Qualifier("slave") DataSource slave) {

        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put(DataSourceConstant.MYSQL, master);
        targetDataSources.put(DataSourceConstant.ORACLE, slave);
        return DynamicDataSource.build(masterDatasource(), targetDataSources);
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(masterDatasource(), slaveDatasource()));
        // 重写的事务factory（解决多数据源切换事务控制不生效的问题） 未测试
        /* String databaseIdentification = DataSourceContextHolder.getDataSource();  = null */
        sqlSessionFactory.setTransactionFactory(new MultiDataSourceTransactionFactory());
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            sqlSessionFactory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        // 方法一：手动指定mapper.xml文件位置
        // sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/**/*Mapper.xml"));
        // 方法二：使用mybatis-autoconfigure 已经自动加载的资源，不手动指定
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            sqlSessionFactory.setMapperLocations(this.properties.resolveMapperLocations());
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            sqlSessionFactory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        MybatisConfiguration configuration = new MybatisConfiguration();
        //configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        // 乐观锁插件
        //PerformanceInterceptor(),OptimisticLockerInterceptor()
        // 添加分页功能
        sqlSessionFactory.setPlugins(paginationInterceptor());
        //sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }

    /*@Bean
    public GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
        conf.setLogicDeleteValue("-1");
        conf.setLogicNotDeleteValue("1");
        conf.setIdType(0);
        //conf.setMetaObjectHandler(new MyMetaObjectHandler());
        conf.setDbColumnUnderline(true);
        conf.setRefresh(true);
        return conf;
    }*/
}
