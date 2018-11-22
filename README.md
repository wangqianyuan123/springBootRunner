# springBootRunner
解决项目启动时初始化资源


在我们实际工作中，总会遇到这样需求，在项目启动的时候需要做一些初始化的操作，比如初始化线程池，提前加载好加密证书等。今天就给大家介绍一个 Spring Boot 神器，专门帮助大家解决项目启动初始化资源操作。

这个神器就是 CommandLineRunner，CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，非常适合在应用程序启动之初进行一些数据初始化的工作。

接下来我们就运用案例测试它如何使用，在测试之前在启动类加两行打印提示，方便我们识别 CommandLineRunner 的执行时机。

1.启动
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.err.println("The service DemoApplication to start.");
		SpringApplication.run(DemoApplication.class, args);
		System.err.println("The service DemoApplication to end.");
	}
}

2.接下来我们直接创建一个类继承 CommandLineRunner ，并实现它的 run() 方法。3.
@Component
@Order(1)
public class OrderRunner1 implements CommandLineRunner  {

	@Override
	public void run(String... args) throws Exception {
		   System.out.println("OrderRunner1 初始化开始");	
	}
  
3.Order来约定启动顺序
@Component
@Order(1)
public class OrderRunner2 implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		  System.out.println("OrderRunner2 初始化开始");	
		
	}

}

4.我们在 run() 方法中打印了一些参数来看出它的执行时机。完成之后启动项目进行测试：

The service DemoApplication to start.

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.0.RELEASE)

2018-11-21 13:07:15.892  INFO 20092 --- [           main] com.victor.DemoApplication               : Starting DemoApplication on Dell-PC with PID 20092 (D:\eclipse\demo\target\classes started by Dell in D:\eclipse\demo)
2018-11-21 13:07:15.894  INFO 20092 --- [           main] com.victor.DemoApplication               : No active profile set, falling back to default profiles: default
2018-11-21 13:07:16.629  INFO 20092 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2018-11-21 13:07:16.649  INFO 20092 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2018-11-21 13:07:16.651  INFO 20092 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet Engine: Apache Tomcat/9.0.12
2018-11-21 13:07:16.660  INFO 20092 --- [           main] o.a.catalina.core.AprLifecycleListener   : The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: [D:\java\jdk\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;D:/java/jre/bin/server;D:/java/jre/bin;D:/java/jre/lib/amd64;C:\ProgramData\Oracle\Java\javapath;C:\Program Files (x86)\Intel\iCLS Client\;C:\Program Files\Intel\iCLS Client\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files (x86)\Intel\UCRT\;C:\Program Files\Intel\UCRT\;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files\Intel\Intel(R) Management Engine Components\DAL;C:\Program Files (x86)\Intel\Intel(R) Management Engine Components\IPT;C:\Program Files\Intel\Intel(R) Management Engine Components\IPT;D:\java\jdk\bin;D:\java\jdk\jre\bin;D:\apache-maven-3.5.3-bin\apache-maven-3.5.3\bin;C:\Program Files\TortoiseSVN\bin;D:\Gradle\gradle-2.3\bin;C:\Program Files\nodejs\;C:\Users\Dell\AppData\Roaming\npm;D:\软件\eclipse-java-mars-2-win32-x86_64\eclipse;;.]
2018-11-21 13:07:16.745  INFO 20092 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2018-11-21 13:07:16.745  INFO 20092 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 813 ms
2018-11-21 13:07:16.766  INFO 20092 --- [           main] o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
2018-11-21 13:07:16.768  INFO 20092 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'characterEncodingFilter' to: [/*]
2018-11-21 13:07:16.769  INFO 20092 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]
2018-11-21 13:07:16.769  INFO 20092 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'formContentFilter' to: [/*]
2018-11-21 13:07:16.769  INFO 20092 --- [           main] o.s.b.w.servlet.FilterRegistrationBean   : Mapping filter: 'requestContextFilter' to: [/*]
2018-11-21 13:07:16.925  INFO 20092 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2018-11-21 13:07:17.142  INFO 20092 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2018-11-21 13:07:17.144  INFO 20092 --- [           main] com.victor.DemoApplication               : Started DemoApplication in 1.46 seconds (JVM running for 1.887)
OrderRunner1 初始化开始
OrderRunner2 初始化开始
The service DemoApplication to end.

5.通过控制台的输出我们发现，添加 @Order 注解的实现类最先执行，并且@Order()里面的值越小启动越早。

CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，非常适合在应用程序启动之初进行一些数据初始化的工作。


6.2018/11/21
task模块中 提交 初始化资源的小技巧 CommandLineRunner @Order指定初始化的顺序

7.2018/11/21
测试模块中  提交邮件发送  并用 thymeleaf 设置邮件动态模板

8.2018/11/21
errController模块中  提交统一异常处理  （错误数据json 和页面err.html）

9.2018/11/21
配置Swagger2 在线文档  并设置密码

10.2018/11/22
springboot 集成mybatis 并搭建多数据源架构
