package br.com.CRUDMVC.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import br.com.CRUDMVC.conversor.TipoSexoConverter;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/resources/bootstrap");
	}
	
	@Bean
	 public SpringResourceTemplateResolver templateResolver(){
	 SpringResourceTemplateResolver templateResolver = new
	 SpringResourceTemplateResolver();
	 templateResolver.setApplicationContext(applicationContext);
	 templateResolver.setPrefix("/WEB-INF/views/");
	 templateResolver.setSuffix(".html");
	 templateResolver.setCharacterEncoding("UTF-8");
	 templateResolver.setTemplateMode(TemplateMode.HTML);
	 templateResolver.setCacheable(false);
	 return templateResolver;
	 }
	
	@Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateEngineMessageSource(messageSource());
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new TipoSexoConverter());
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("messages");
		source.setDefaultEncoding("UTF-8");
		return source;
	}
	
}
