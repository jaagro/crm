package com.jaagro.crm.web.config;

import com.dianping.cat.servlet.CatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tony
 */
@Configuration
public class CatFilterConfigure {

    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatFilter filter = new CatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

//    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        //dataSource
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        //mybatisPlugin
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new CatMybatisPlugin()});
//        //mapperLocation
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:/mapper/*.xml"));
//        return sqlSessionFactoryBean;
//    }
}
