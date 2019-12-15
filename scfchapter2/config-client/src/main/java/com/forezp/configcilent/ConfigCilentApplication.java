package com.forezp.configcilent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ConfigCilentApplication {

  /*
   *  场景：
   *  某个Client发送一个刷新Git配置文件的请求。
   *  然后通过消息总线向其它服务器传播此通知，通知所有的服务器更新配置文件，
   *  从而使整个微服务集群都达到更新配置文件的目的
   */
  public static void main(String[] args) {
    SpringApplication.run(ConfigCilentApplication.class, args);
  }

}
