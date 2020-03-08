package phonosemantics.language.languageReduced;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.language.Language;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

public class LanguageReducedService {
    private static final Logger userLogger = LogManager.getLogger(Language.class);


    public static ArrayList<String> getAllLanguageNames() {
        userLogger.info("getting all languages by name");
        HashMap<String, Language> allLanguages = Language.getAllLanguages();

        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Language> lang : allLanguages.entrySet()) {
            result.add(lang.getKey());
        }
        return result;
    }
}
