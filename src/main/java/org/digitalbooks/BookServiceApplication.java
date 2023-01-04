package org.digitalbooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class BookServiceApplication {
    public static void main(String[] args) {
        System.out.println("Use this URL for accessing swaggerUI: http://localhost:8081/swagger-ui/index.html#/");
        SpringApplication.run(BookServiceApplication.class, args);
    }
}