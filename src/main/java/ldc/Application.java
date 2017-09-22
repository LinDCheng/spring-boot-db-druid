package ldc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Created by Administrator on 2017/9/19.
 */
@SpringBootApplication
@EnableRedisRepositories
@ComponentScan(basePackages = "ldc")
//@ServletComponentScan(basePackages = {"ldc.servlet"}) //使用注解的方式配置druid监控和过滤器,因为这里使用配置方式所以注释,注解方式详看servlet包的两个配置(该注解不可少,否则启动访问监控报404)
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
