汇总笔记  整理
注解+http协议
demo1
1、环境准备
     IDE+JDK1.8以上+maven+oracle/mysql  +调试接口工具（postman）
     repository仓库
     IDEA配置maven地址  配置settings.xml的Repository地址
      <localRepository>D:\developSoftware\repository\repository</localRepository>

2、spring-boot成果展示：Greetings from Spring Boot!

首先从程序的入口SpringApplication.run(DemoApplication.class, args);开始分析：

1).SpringApplication是Spring Boot框架中描述Spring应用的类，
它的run()方法会创建一个Spring应用上下文（Application Context）。
另一方面它会扫描当前应用类路径上的依赖，
例如本例中发现spring-webmvc（由 spring-boot-starter-web传递引入）在类路径中，
那么Spring Boot会判断这是一个Web应用，
并启动一个内嵌的Servlet容器（默认是Tomcat）用于处理HTTP请求。

2).Spring WebMvc框架会将Servlet容器里收到的HTTP请求根据路径分发给对应的@Controller类进行处理，@RestController是一类特殊的@Controller，
它的返回值直接作为HTTP Response的Body部分返回给浏览器。


3).@RequestMapping注解表明该方法处理那些URL对应的HTTP请求，也就是我们常说的URL路由（routing)，请求的分发工作是有Spring完成的。
例如上面的代码中http://localhost:8080/greeting根路径就被路由至greeting()方法进行处理。
如果访问http://localhost:8080/hello，则会出现404 Not Found错误，因为我们并没有编写任何方法来处理/hello请求。

4)@SpringBootApplication申明让spring boot自动给程序进行必要的配置
   该 @SpringBootApplication 注解等价于以默认属性使用 @Configuration,@EnableAutoConfiguration 和 @ComponentScan 。
   @EnableAutoConfiguration。开启自动配置  这个注解告诉Spring Boot根据添加的jar依赖猜测你想如何配置Spring。
   由于spring-boot-starter-web添加了Tomcat和Spring MVC，
   所以auto-configuration将假定你正在开发一个web应用并相应地对Spring进行设置。
   @ComponentScan注解自动收集所有的Spring组件，包括@Configuration类。

5).pom.xml解析
首先是增加了<parent>，增加父pom比较简单，而且spring-boot-starter-parent包含了大量配置好的依赖管理，在自己项目添加这些依赖的时候不需要写<version>版本号。
其次java.version属性，Spring默认使用jdk1.6，该项目中使用jdk1.8，需要在pom.xml的属性里面添加java.version：
再次添加spring-boot-maven-plugin插件
  该插件支持多种功能，常用的有两种，第一种是打包项目为可执行的jar包。

  在项目根目录下执行mvn package将会生成一个可执行的jar包，jar包中包含了所有依赖的jar包，只需要这一个jar包就可以运行程序，使用起来很方便。该命令执行后还会保留一个XXX.jar.original的jar包，包含了项目中单独的部分。

  生成这个可执行的jar包后，在命令行执行java -jar xxxx.jar即可启动项目。

  另外一个命令就是mvn spring-boot:run，可以直接使用tomcat（默认）启动项目。
6)Spring-Loaded项目提供了强大的热部署功能，添加/删除/修改 方法/字段/接口/枚举 等代码的时候都可以热部署，速度很快，很方便。
    想在Spring Boot中使用该功能非常简单，就是在spring-boot-maven-plugin插件下面添加依赖：
    <dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
            <version>1.2.5.RELEASE</version>
        </dependency>
    </dependencies>

    添加以后，通过mvn spring-boot:run启动就支持热部署了。
------------------------------------------

demo2
1、从前台传变量到后台的两种方式@RequestParam 和 @PathVariable
   @PathVariable和@RequestParam，分别是从路径里面去获取变量，也就是把路径当做变量，后者是从请求里面获取参数。
2、一个controller中可以处理多个不同的请求
3、@Data在类上标注，类中的属性自动有get，set方法
4、如果定义的是final类型，是没有set方法的，而且必须初始化。
5、REST 和 HATEOAS相关概念
满足 HATEOAS 约束的 REST 服务最大的特点在于服务器提供给客户端的表达中包含了动态的链接信息
，客户端通过这些链接来发现可以触发状态转换的动作。
REST 是一种架构风格，它包含了一个分布式超文本系统中对于组件、连接器和数据（API first约定数据格式）的约束。
REST 的约束包括：
客户端-服务器结构。通过一个统一的接口来分开客户端和服务器，使得两者可以独立开发和演化。客户端的实现可以简化，而服务器可以更容易的满足可伸缩性的要求。
无状态。在不同的客户端请求之间，服务器并不保存客户端相关的上下文状态信息。任何客户端发出的每个请求都包含了服务器处理该请求所需的全部信息。
可缓存。客户端可以缓存服务器返回的响应结果。服务器可以定义响应结果的缓存设置。

分层的系统。在分层的系统中，可能有中间服务器来处理安全策略和缓存等相关问题，以提高系统的可伸缩性。客户端并不需要了解中间的这些层次的细节。
按需代码（可选）。服务器可以通过传输可执行代码的方式来扩展或自定义客户端的行为。这是一个可选的约束。

统一接口。该约束是 REST 服务的基础，是客户端和服务器之间的桥梁。该约束又包含下面 4 个子约束。资源标识符。每个资源都有各自的标识符。客户端在请求时需要指定该标识符。在 REST 服务中，该标识符通常是 URI。客户端所获取的是资源的表达（representation），通常使用 XML 或 JSON 格式。
通过资源的表达来操纵资源。客户端根据所得到的资源的表达中包含的信息来了解如何操纵资源，比如对资源进行修改或删除。
自描述的消息。每条消息都包含足够的信息来描述如何处理该消息。

超媒体作为应用状态的引擎（HATEOAS）。客户端通过服务器提供的超媒体内容中动态提供的动作来进行状态转换。这也是本文所要介绍的内容。

对于不使用 HATEOAS 的 REST 服务，客户端和服务器的实现之间是紧密耦合的。
客户端需要根据服务器提供的相关文档来了解所暴露的资源和对应的操作。
当服务器发生了变化时，如修改了资源的 URI，客户端也需要进行相应的修改。
而使用 HATEOAS 的 REST 服务中，客户端可以通过服务器提供的资源的表达来智能地发现可以执行的操作。
当服务器发生了变化时，客户端并不需要做出修改，因为资源的 URI 和其他信息都是动态发现的。
---------------------------------------------------------------------------------------
demo3
获取动态的链接信息

满足 HATEOAS 约束的 REST 服务最大的特点在于服务器提供给客户端的表达中包含了动态的链接信息，
客户端通过这些链接来发现可以触发状态转换的动作。
Spring HATEOAS 的主要功能在于提供了简单的机制来创建这些链接，并与 Spring MVC 框架有很好的集成。
1、添加Spring HATEOAS 的 Maven 依赖声明
    <dependency>
			<groupId>org.springframework.hateoas</groupId>
			<artifactId>spring-hateoas</artifactId>
			<version>0.19.0.RELEASE</version>
    </dependency>
2、Spring HATEOAS 使用 org.springframework.hateoas.Link 类来表示链接。
    Spring HATEOAS 提供了 org.springframework.hateoas.mvc.ControllerLinkBuilder 来解决这个问题
    Link 类遵循 Atom 规范中对于链接的定义，包含 rel 和 href 两个属性。
    属性 rel 表示的是链接所表示的关系（relationship），href 表示的是链接指向的资源标识符，一般是 URI。
    资源通常都包含一个属性 rel 值为 self 的链接，用来指向该资源本身。
3、实现获取链接的两种方式，通过 ControllerLinkBuilder 类的 linkTo 方法，先指定 Spring MVC 控制器的 Java 类，
   再通过 slash 方法来找到下一级的路径，最后生成属性值为 self 的链接。
   在使用 ControllerLinkBuilder 生成链接时，除了可以使用控制器的 Java 类之外，还可以使用控制器 Java 类中包含的方法。
   1)linkTo(HelloController.class).slash(name).withSelfRel()
   2)linkTo(methodOn(HelloController.class).greeting(name)).withSelfRel()

-------------------------------------------------------------------------------------
demo
数据库操作（在pox.xml中的配置）如下所示
<dependency>
   <groupId>mysql</groupId>
   <artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

application.properties配置：
#https://segmentfault.com/a/1190000004316491
########################################################
###datasource
########################################################
spring.datasource.url = jdbc:mysql://localhost:3306/test
spring.datasource.username = root
spring.datasource.password = root
spring.datasource.driverClassName = com.mysql.jdbc.Driver
#指定连接池中最大的活跃连接数
spring.datasource.max-active=20
#指定连接池最大的空闲连接数量.
spring.datasource.max-idle=8
#指定连接池最小的空闲连接数量.
spring.datasource.min-idle=8
#指定启动连接池时，初始建立的连接数量
spring.datasource.initial-size=10

定制化数据源 连接阿里巴巴的数据源
----------------------------------------------------------------------
详细注解
  @Service用于标注业务层组件，
  @Controller用于标注控制层组件（如struts中的action）
  @Repository用于标注数据访问组件，即DAO组件，
  @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
rest
微服务之间rpt
--------------------------------------------------------------------------
注解：
  @PathVariable和@RequestParam，分别是从路径里面去获取变量，也就是把路径当做变量，后者是从请求里面获取参数。
  get：向服务器请求资源
  post：向服务器提交资源








