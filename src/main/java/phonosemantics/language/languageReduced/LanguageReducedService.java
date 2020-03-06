package phonosemantics.language.languageReduced;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.App;
import phonosemantics.language.Language;
import phonosemantics.word.WordReduced;
import phonosemantics.word.wordlist.wordlistReduced.WordListReduced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class LanguageReducedService {
    private static final Logger userLogger = LogManager.getLogger(Language.class);

    //private static HashSet<LanguageReduced> allLanguages;


    public static ArrayList<String> getAllLanguageNames() {
        HashMap<String, Language> allLanguages = Language.getAllLanguages();

        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Language> lang : allLanguages.entrySet()) {
            result.add(lang.getKey());
        }
        return result;
    }

    /*public static HashSet<LanguageReduced> getAllLanguageNames() {
        if (allLanguages != null) {
            return allLanguages;
        } else {
            allLanguages = new HashSet<>();
            HashSet<String> set = new HashSet<>();
            for (WordListReduced wlr : App.getAllReducedWordLists()) {
                for (WordReduced wr : wlr.getList()) {
                    if (!set.contains(wr.getLanguage().getTitle())) {
                        set.add(wr.getLanguage().getTitle());
                        allLanguages.add(wr.getLanguage());
                    }
                }
            }
            return allLanguages;
        }
    }*/

    /*public static LanguageReduced getLanguageByTitle(String title) {
        for (LanguageReduced lr : getAllLanguageNames()) {
            if (lr.getTitle().toLowerCase().equals(title.toLowerCase())) {
                return lr;
            }
        }
        userLogger.error("requested language " + title + " was not found");
        return null;
    }*/

    // TODO REMOVE
    public static Language getLanguageByTitle(String title) {
        Language lang = Language.getAllLanguages().get(title);
        if (lang == null) {
            userLogger.error("requested language " + title + " was not found");
        }
        return Language.getAllLanguages().get(title);
    }
}
