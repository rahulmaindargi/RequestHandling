package rahul.api.userdetails.dba.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "rahul.api.userdetails.dba")
@Log4j2
public class DataConfiguration {


    @Bean("entityManagerFactory")
    @Retryable(
            value = {Exception.class},
            maxAttempts = 20,
            backoff = @Backoff(delay = 5000))
    public LocalEntityManagerFactoryBean entityManagerFactory()   {
        LocalEntityManagerFactoryBean bean = new LocalEntityManagerFactoryBean();
        bean.setPersistenceUnitName("postgres");
        return bean;
    }
    @Bean
    public BeanPostProcessor entityMgrFactoryWrapper() {
        return new EntityManagerPostProcess();
    }
    @Bean("jpaTransactionManager")
    public JpaTransactionManager jpaTransactionManager() {
        LocalEntityManagerFactoryBean entityManagerFactory = entityManagerFactory();
        log.info("### entityManagerFactory {}" , entityManagerFactory);
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    /*@Bean("dataSource")
    public DataSource dataSource() {
        LocalEntityManagerFactoryBean entityManagerFactory= entityManagerFactory();
        System.out.println("### Datasource"+entityManagerFactory);
        DataSource dataSource=entityManagerFactory.getDataSource();
        System.out.println("### Datasource Value"+dataSource);
        return dataSource;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        System.out.println("### Datasource"+dataSource);
        return new JdbcTemplate(dataSource);
    }*/

}

