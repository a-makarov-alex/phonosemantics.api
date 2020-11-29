package phonosemantics.word;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.PhonemeInTable;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticulationPattern {
    private static final Logger userLogger = LogManager.getLogger(ArticulationPattern.class);

    private String graphicForm;
    private List<String> transcription;
    private String base;    //основание для создания паттерна: vocoid/continuant/backness etc
    private String language;
    private Object[] pattern;

    public ArticulationPattern(Word word, String base) {
        this.graphicForm = word.getGraphicForm();
        this.transcription = word.getTranscription();
        this.base = base;
        this.language = word.getLanguage();
        this.pattern = calculatePattern();
    }

    /**
     * DEFINING ARTICULATION PATTERN ON THE BASE SPECIFIED EXPLICITLY
     */
    private Object[] calculatePattern() {
        Object[] pattern = new Object[this.transcription.size()];

        for (int i = 0; i < this.transcription.size(); i++) {
            PhonemeInTable ph = PhonemesBank.getInstance().find(transcription.get(i));
            if (ph == null) {
                userLogger.info("Unknown phoneme " + transcription.get(i) + " in transcription for word " + this.getGraphicForm());
                return null;
            }

            if (ph.getDistinctiveFeatures() == null) {
                userLogger.info("Distinctive features are not specified: " + ph.getValue() + " phoneme");
                pattern[i] = "NO_INFO";
            } else {
                switch (base.toLowerCase()) {
                    // Major Class
                    case "vocoid" : { pattern[i] = ph.getDistinctiveFeatures().getMajorClass().isVocoid(); break; }
                    case "approximant" : { pattern[i] = ph.getDistinctiveFeatures().getMajorClass().isApproximant(); break; }
                    // Manner
                    case "mannerprecise" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getMannerPrecise(); break; }
                    case "stricture" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getStricture(); break; }
                    case "sonorant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isSonorant(); break; }
                    case "continuant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isContinuant(); break; }
                    case "nasal" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isNasal(); break; }
                    case "strident" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getStrident(); break; }
                    case "sibilant" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getSibilant(); break; }
                    case "semivowel" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getSemivowel(); break; }
                    case "lateral" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getLateral(); break; }
                    case "rhotics" : { pattern[i] = ph.getDistinctiveFeatures().getManner().getRhotics(); break; }
                    case "voiced" : { pattern[i] = ph.getDistinctiveFeatures().getManner().isVoiced(); break; }
                    // Place
                    case "placeapproximate" : { pattern[i] = ph.getDistinctiveFeatures().getPlace().getPlaceApproximate(); break; }
                    case "placeprecise" : { pattern[i] = ph.getDistinctiveFeatures().getPlace().getPlacePrecise(); break; }
                    // Vowel Space
                    case "height" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getHeight(); break; }
                    case "backness" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getBackness(); break; }
                    case "roundness" : { pattern[i] = ph.getDistinctiveFeatures().getVowelSpace().getRoundness(); break; }

                    default : {
                        userLogger.info("base " + base + " has not proper value for getting articulation pattern");
                        break;
                    }
                }
            }
        }
        return pattern;
    }
}