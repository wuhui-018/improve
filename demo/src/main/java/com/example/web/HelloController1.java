package com.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tanglei-010 on 2016/7/21.
 */
@Controller
@EnableAutoConfiguration
public class HelloController1 {
  @RequestMapping("/EnableAutoConfiguration")
  @ResponseBody
  String home() {
    return "Hello World!";
  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(HelloController1.class, args);
  }
}
