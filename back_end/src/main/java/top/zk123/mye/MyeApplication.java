package top.zk123.mye;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MyeApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MyeApplication.class, args);
    }

    // 注意这里要指向原先用main方法执行的Application启动类
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MyeApplication.class);
    }
}
