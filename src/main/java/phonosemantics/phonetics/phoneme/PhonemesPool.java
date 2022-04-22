package phonosemantics.phonetics.phoneme;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.LoggerConfig;
import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.PlaceApproximate;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Height;
import phonosemantics.statistics.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PhonemesPool {
    private static final Logger userLogger = Logger.getLogger(PhonemesPool.class);

    private List<String> transcription; // транскрипция слова в виде последовательности знаков, обозначающих фонемы. Удобно выводить на экран для чтения
    private double reliability; //TODO: показатель, рассчитываемый по наличию фонем в транскрипции в фонемном составе языка. Берется из справочника. Формула, например, средн арифм.

    // Первые и последние звуки. TODO: хз что делать, если слово с 1 гласной или 1 согласной, они будут попадать и туда, и туда
    private PhonemeInTable initialConsonant;
    private PhonemeInTable initialVowel;
    private PhonemeInTable finalConsonant;
    private PhonemeInTable finalVowel;

    private List<PhonemeInTable> transcriptionFull; // транскрипция в полном виде, удобная для расчётов


    public PhonemesPool(String graphicForm) {
        this.transcriptionFull = getTranscriptionFromWord(graphicForm);
        this.transcription = transcriptionFull.stream()
                .map(PhonemeInTable::getValue)
                .collect(Collectors.toList());
        this.initialConsonant = transcriptionFull.stream()
                .filter(phoneme -> phoneme.getDistinctiveFeatures().getVowelSpace().getHeight().equals(Height.NOT_APPLICABLE))
                .findFirst().orElse(null);
        this.initialVowel = transcriptionFull.stream()
                .filter(phoneme -> phoneme.getDistinctiveFeatures().getPlace().getPlaceApproximate().equals(PlaceApproximate.NOT_APPLICABLE))
                .findFirst().orElse(null);
        this.finalConsonant = transcriptionFull.stream()
                .filter(phoneme -> phoneme.getDistinctiveFeatures().getVowelSpace().getHeight().equals(Height.NOT_APPLICABLE))
                .reduce(null, (x, y) -> y);
        this.finalVowel = transcriptionFull.stream()
                .filter(phoneme -> phoneme.getDistinctiveFeatures().getPlace().getPlaceApproximate().equals(PlaceApproximate.NOT_APPLICABLE))
                .reduce(null, (x, y) -> y);

        // TODO: расчёт reliability !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }


    private List<PhonemeInTable> getTranscriptionFromWord(String graphicForm) {
        List<PhonemeInTable> result = new ArrayList<>();

        if (graphicForm != null) {
            String[] letters = graphicForm.split("");
            // TODO remove Map<String, Phoneme> allPhonemes = SoundsBank.getInstance().getAllPhonemesTable();
//Language language = Language.getLanguage(this.getLanguage());

            // Phoneme might be a set of 2 symbols.
            // So we need to check the symbol after the current on every step.
            for (int i = 0; i < graphicForm.length(); i++) {

                // For extra symbols, accents, tones etc.
                if (!PhonemesBank.isExtraSign(letters[i])) {

                    // For last symbol
                    if (i == graphicForm.length() - 1) {
                        //userLogger.info("LAST SYMBOL FOUND: " + letters[i]);
                        PhonemeInTable ph = PhonemesBank.getInstance().find(letters[i]);
                        if (ph != null) {
                            result.add(ph);
                            //if (language != null) {
//language.categorizePh(ph);
//}
                        } else {
                            Statistics.addUnknownSymbol(letters[i]);
                        }
                        Statistics.incrementNumOfAllPhonemes();
                    } else {
                        // For 2-graph phoneme
                        //userLogger.info("REQUEST DIGRAPH: " + letters[i] + letters[i + 1]);
                        PhonemeInTable ph = PhonemesBank.getInstance().find(letters[i] + letters[i + 1]);
                        if (ph != null) {
                            //userLogger.info("DIGRAPH: " + letters[i] + letters[i + 1]);
                            result.add(ph);
                            Statistics.incrementNumOfAllPhonemes();
//if (language != null) {
//language.categorizePh(ph);
//}
                            i++;
                        } else {
                            // For 1-graph phoneme
                            //userLogger.info("1-GRAPH: " + letters[i]);
                            ph = PhonemesBank.getInstance().find(letters[i]);
                            if (ph != null) {
                                result.add(ph);
                                Statistics.incrementNumOfAllPhonemes();
//if (language != null) {
//language.categorizePh(ph);
//}
                            } else {
                                // Empty phoneme
                                Statistics.addUnknownSymbol(letters[i]);
                                Statistics.incrementNumOfAllPhonemes();
                            }
                        }
                    }
                } else {
                    if (LoggerConfig.CONSOLE_EXTRA_SYMBOLS) {
                        userLogger.info("Extra: " + letters[i]);
                    }
                }
            }
        }
        return result;
    }


    // расчёт достоверности
    private void countReliability() {
        // TODO: пройти по справочнику "язык - фонемный состав" для каждой фонемы
        // вариант расчёта: наличие 1, отсутствие 0, сомнение 0.5 - сумма/число фонем в слове.
    }
}
