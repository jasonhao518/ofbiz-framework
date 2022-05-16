package org.apache.ofbiz.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void configureViewResolvers (ViewResolverRegistry registry) {
      registry.freeMarker();
  }

  @Bean
  public FreeMarkerConfigurer freeMarkerConfigurer() {
      FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
      configurer.setTemplateLoaderPath("/WEB-INF/views/");
      return configurer;
  }
}