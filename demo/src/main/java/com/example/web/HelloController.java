package com.example.web;

import com.example.entity.Greeting;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.atomic.AtomicLong;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by tanglei-010 on 2016/7/20.
 */
@RestController
public class HelloController {
  @RequestMapping("/greet")
  public String greeting(){
    return "Greetings from Spring Boot";
  }


  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  //地址栏传值的第一种方式，访问方式http://localhost:8080/greeting?name=wuhui
  //获取头参数，获取对象数据用requestbody
  // 或者http://localhost:8080/greeting
  @RequestMapping("/greeting")
  public Greeting  greeting(@RequestParam(value="name", defaultValue="World") String name) {
//    linkTo(HelloController.class).slash(name).withSelfRel();
//    return new Greeting(counter.incrementAndGet(),
//            String.format(template, name),String.format(template,linkTo(HelloController.class).slash(name).withSelfRel()));
    return new Greeting(counter.incrementAndGet(),
           String.format(template, name),linkTo(methodOn(HelloController.class).greeting(name)).withSelfRel().toString());
  }


  //地址栏传值的第二种方式，访问方式http://localhost:8080/users/wuhui
  @RequestMapping("/users/{username}")
  public String userProfile(@PathVariable("username") String username) {
    return String.format("user %s", username);
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginGet() {
    return "Login Page";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String loginPost() {
    return "Login Post Request";
  }
}
