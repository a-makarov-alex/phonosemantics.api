package phonosemantics;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import phonosemantics.data.PortConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.util.Collections;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class App {
    private static final Logger userLogger = Logger.getLogger(App.class);

    // Удаление файла не работает. Требуется разобраться
    /*static {
        try {
            String logsPath = "src/main/resources/logs.log";
            File logs = new File(logsPath);
            // Удаляем предыдущий файл, если он существует
            logs.delete();
            logs = new File("src/main/resources", "logs.log");
        }
        catch (Exception e) { userLogger.error(e); }
    }*/

    public static void main(String args[]) {
        //SpringApplication.run(App.class, args);
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", PortConfig.BACKEND_PORT));
        app.run(args);
    }
}
