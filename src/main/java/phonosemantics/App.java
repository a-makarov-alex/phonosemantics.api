package phonosemantics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import phonosemantics.word.wordlist.WordList;
import phonosemantics.word.wordlist.WordListService;
import phonosemantics.word.wordlist.wordlistReduced.WordListReduced;
import phonosemantics.word.wordlist.wordlistReduced.WordlistReducedService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class App {
    private static final Logger userLogger = LogManager.getLogger(App.class);

    // TODO: запихнуть в контекст эти данные, если мы не оставим путь как константу
    // TODO: add some kind of context to project to store data
    private static ArrayList<WordList> allWordLists;
    public static ArrayList<WordListReduced> allReducedWordLists;

    public static ArrayList<WordList> getAllWordLists() {
        if (allWordLists != null) {
            userLogger.info("allWordlists is NOT null");
            return allWordLists;
        } else {
            userLogger.info("allWordlists is null");
            allWordLists = WordListService.readAllWordListsFromInputFile();
            return allWordLists;
        }
    }

    public static ArrayList<WordListReduced> getAllReducedWordLists() {
        allReducedWordLists = WordlistReducedService.readAllReducedWordlistsFromInputFile();
        return allReducedWordLists;
    }

    public static void main(String args[]) {
        //SpringApplication.run(App.class, args);
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
}
