package dev.opal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;

@SpringBootApplication(scanBasePackages = "dev.opal.app", 
        nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class)
public class OpalCustomApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpalCustomApplication.class, args);
    }
}