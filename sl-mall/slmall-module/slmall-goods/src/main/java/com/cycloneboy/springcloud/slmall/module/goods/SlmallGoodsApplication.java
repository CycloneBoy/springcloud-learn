package com.cycloneboy.springcloud.slmall.module.goods;

import com.cycloneboy.springcloud.slmall.common.config.Swagger2Config;
import com.cycloneboy.springcloud.slmall.common.redis.JedisClientPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringCloudApplication
//可按需引入全局异常拦截、Swagger2、Redis等配置类[当然推荐配置扫描包]
//@ComponentScan({"com.cycloneboy.springcloud.slmall.common",
//		"com.cycloneboy.springcloud.slmall.module.portal"})
@Import({Swagger2Config.class, JedisClientPool.class})
//启用JPA审计
@EnableJpaAuditing
//@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class SlmallGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlmallGoodsApplication.class, args);
	}

}
