package pl.connectis.cinemareservationsapp.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.nio.charset.StandardCharsets;

public class ThymeleafAutoConfiguration {

    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setContentType("application/json");
        viewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[] {"*.json"});
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

}
