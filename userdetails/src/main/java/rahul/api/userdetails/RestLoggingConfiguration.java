package rahul.api.userdetails;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import rahul.api.userdetails.logging.filter.Log4j2LoggingFilter;

@Configuration
public class RestLoggingConfiguration extends WebMvcConfigurerAdapter {
    /*@Autowired
    private Log4j2LoggingFilter log4j2LoggingFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(log4j2LoggingFilter)
                .addPathPatterns("/**//*");

    }*/
}
