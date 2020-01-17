package phonosemantics.language.languageReduced;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.App;
import phonosemantics.language.Language;
import phonosemantics.word.WordReduced;
import phonosemantics.word.wordlist.wordlistReduced.WordListReduced;

import java.util.HashSet;

public class LanguageReducedService {
    private static final Logger userLogger = LogManager.getLogger(Language.class);

    private static HashSet<LanguageReduced> allLanguages;

    public static HashSet<LanguageReduced> getAllLanguages() {
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
    }

    public static LanguageReduced getLanguageByTitle(String title) {
        for (LanguageReduced lr : getAllLanguages()) {
            if (lr.getTitle().toLowerCase().equals(title.toLowerCase())) {
                return lr;
            }
        }
        userLogger.error("requested language " + title + " was not found");
        return null;
    }
}
