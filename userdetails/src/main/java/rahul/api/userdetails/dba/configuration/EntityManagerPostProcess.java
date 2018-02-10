package rahul.api.userdetails.dba.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class EntityManagerPostProcess implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof LocalEntityManagerFactoryBean) {
            bean = new RetryableLocalEntityManagerFactory((LocalEntityManagerFactoryBean) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RetryableLocalEntityManagerFactory) {
            bean = ((RetryableLocalEntityManagerFactory) bean).getDelegate();
        }
        return bean;
    }
}
