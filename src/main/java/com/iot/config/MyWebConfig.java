package com.iot.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
@EnableWebMvc
public class MyWebConfig extends WebMvcConfigurerAdapter {

    // get a configured Hibernate4Module
    // here as an example with a disabled USE_TRANSIENT_ANNOTATION feature
    private Hibernate4Module hibernate4Module() {
        return new Hibernate4Module().disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
    }

    // create the ObjectMapper with Spring's Jackson2ObjectMapperBuilder
    // and passing the hibernate4Module to modulesToInstall()
    private MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                            .modulesToInstall(hibernate4Module());
        return new MappingJackson2HttpMessageConverter(builder.build());
  }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }
}
