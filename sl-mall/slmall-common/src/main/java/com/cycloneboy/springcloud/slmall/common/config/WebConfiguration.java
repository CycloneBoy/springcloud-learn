package com.cycloneboy.springcloud.slmall.common.config;

import java.util.List;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Qinyi.
 */
//@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>>
        converters) {

//        converters.clear();
//        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
