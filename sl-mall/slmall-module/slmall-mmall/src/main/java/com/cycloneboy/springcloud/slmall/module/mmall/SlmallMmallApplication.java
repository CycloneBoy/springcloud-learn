package com.cycloneboy.springcloud.slmall.module.mmall;

import com.cycloneboy.springcloud.slmall.common.config.Swagger2Config;
import com.cycloneboy.springcloud.slmall.common.redis.JedisClientPool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Import({Swagger2Config.class, JedisClientPool.class})
//启用JPA审计
@EnableJpaAuditing
//@EnableCaching
@EnableSwagger2
@Transactional
@SpringBootApplication
public class SlmallMmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlmallMmallApplication.class, args);
	}

}
