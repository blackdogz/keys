package com.forezp.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SpringBoot的核心思想就是约定优于配置!
 * 所以仓库中的配置文件会被转换成web接口，访问可以参照以下的规则：
 *    /{application}/{profile}[/{label}]
 *    /{application}-{profile}.yml
 *    /{label}/{application}-{profile}.yml
 *    /{application}-{profile}.properties
 *    /{label}/{application}-{profile}.properties
 *
 * profile为分支名称
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConfigServerApplication.class, args);
  }

}
