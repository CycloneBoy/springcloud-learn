package com.cycloneboy.springcloud.slmall.module.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringCloudApplication
//可按需引入全局异常拦截、Swagger2、Redis等配置类[当然推荐配置扫描包]
@ComponentScan({"com.cycloneboy.springcloud.slmall.common",
		"com.cycloneboy.springcloud.slmall.module.portal"})
//@Import({RestCtrlExceptionHandler.class, Swagger2Config.class})
//启用JPA审计
@EnableJpaAuditing
//@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SlmallPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlmallPortalApplication.class, args);
	}

}
