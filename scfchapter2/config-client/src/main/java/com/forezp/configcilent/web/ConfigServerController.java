package com.forezp.configcilent.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 被刷新从配置文件中的类需要增加@RefreshScope注解
 */
@RefreshScope
@RestController
public class ConfigServerController {

  @Value("${jay.label}")
  private String label;

  @RequestMapping("/hello")
  public String test() {
    return label;
  }

}
