package com.kyojin.tawsila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(
        exclude = {
                JpaRepositoriesAutoConfiguration.class
        }
)
@ImportResource("classpath:applicationContext.xml")
public class TawsilaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TawsilaApplication.class, args);
    }

}
