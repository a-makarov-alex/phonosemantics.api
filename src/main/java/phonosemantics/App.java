package phonosemantics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import phonosemantics.data.Port;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class App {
    public static void main(String args[]) {
        //SpringApplication.run(App.class, args);
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", Port.BACKEND_PORT));
        app.run(args);
    }
}
