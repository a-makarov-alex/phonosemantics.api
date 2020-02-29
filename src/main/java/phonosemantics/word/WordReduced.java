package phonosemantics.word;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.language.Language;
import phonosemantics.language.languageReduced.LanguageReduced;

import java.util.ArrayList;

public class WordReduced {
    private static final Logger userLogger = LogManager.getLogger(Word.class);

    private String graphicForm;
    private ArrayList<String> transcription = new ArrayList<>();
    private String definition;
    private LanguageReduced language;
    private int length = 0;

    public WordReduced(Word word) {
        this.graphicForm = word.getGraphicForm();
        this.definition = word.getMeaning();
        this.language = new LanguageReduced(Language.getLanguage(word.getLanguage()));
        this.length = word.getLength();

        for (int i=0; i < word.getTranscription().size(); i++) {
            this.transcription.add(word.getTranscription().get(i));
        }
    }

    public String getGraphicForm() {
        return graphicForm;
    }

    public String getDefinition() {
        return definition;
    }

    public LanguageReduced getLanguage() {
        return language;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<String> getTranscription() {
        return transcription;
    }
}
