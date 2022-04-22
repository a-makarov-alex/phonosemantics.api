package phonosemantics.phonetics;

import lombok.Data;
import org.apache.log4j.Logger;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word2022;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PatternSquare {
    private static final Logger userLogger = Logger.getLogger(PatternSquare.class);
    // Один вордлист - один квадрат. Описание см. в корне проекта в README.

    // Внешний слой: признаки первого звука в паре
    // Средний слой: признаки второго звука в паре
    // Внутренний слой: число обнаруженных паттернов
    private Map<String, Map<String, Map<String, Map<String, Integer>>>> patternSquare;

    public PatternSquare(List<Word2022> words) {
        // нужно взять первое поле первой фонемы - и все поля второй фонемы. проставить в таблицу
        // затем второе поле и опять все поля второй фонемы
        // таким образом идем до конца слова

        // TODO нарисовать квадрат, чтобы было понятно, зачем 4 вложенных мапы
        patternSquare = new HashMap<>();
        Map<String, Map<String, Integer>> outerTypeLayer = DistinctiveFeatures.getFeaturesStructureDraftStringKeys(DistinctiveFeatures.Type.ALL);

        // Внешний уровень (1): ТИП по вертикали
        // Уровень (2): ПОДТИП по вертикали
        // Уровень (3): ТИП по горизонтали
        // Уровень (4): ПОДТИП по горизонтали, у которого уже прописываются значения.
        for (Map.Entry<String, Map<String, Integer>> outerTypeEntry : outerTypeLayer.entrySet()) {
            String otKey = outerTypeEntry.getKey();
            patternSquare.put(otKey, new HashMap<>());

            for (Map.Entry<String, Integer> outerSubtypeEntry : outerTypeEntry.getValue().entrySet()) {
                String ostKey = outerSubtypeEntry.getKey();
                patternSquare.get(otKey).put(ostKey, new HashMap<>());

                for (Map.Entry<String, Map<String, Integer>> innerTypeEntry : outerTypeLayer.entrySet()) {
                    String itKey = innerTypeEntry.getKey();
                    patternSquare.get(otKey).get(ostKey).put(itKey, new HashMap<>());

                    for (Map.Entry<String, Integer> innerSubtypeEntry : innerTypeEntry.getValue().entrySet()) {
                        String istKey = innerSubtypeEntry.getKey();
                        patternSquare.get(otKey).get(ostKey).get(itKey).put(istKey, 0);
                    }
                }
            }
        }
        // На данный момент имеется пустая структура с нулями во всех конечных точках

        PhonemesBank phBank = PhonemesBank.getInstance();
        for (Word2022 w : words) {
            List<String> tr = w.getPhonemesPool().getTranscription();
            for (int i = 1; i < tr.size(); i++) {
                PhonemeInTable ph = phBank.find(tr.get(i-1));
                PhonemeInTable nextPh = phBank.find(tr.get(i));

                Map<String, Map<String, Integer>> phFeat = ph.countPhonemeDistinctiveFeaturesInstances();
                Map<String, Map<String, Integer>> nextPhFeat = nextPh.countPhonemeDistinctiveFeaturesInstances();

                for (Map.Entry<String, Map<String, Integer>> outerType : phFeat.entrySet()) {
                    for (Map.Entry<String, Integer> outerSubType : outerType.getValue().entrySet()) {
                        if (outerSubType.getValue() > 0) {
                            for (Map.Entry<String, Map<String, Integer>> innerType : nextPhFeat.entrySet()) {
                                for (Map.Entry<String, Integer> innerSubtype : innerType.getValue().entrySet()) {
                                    if (innerSubtype.getValue() > 0) {
                                        // Инкрементим текущее значение в квадрате на единицу, если найден признак
                                        Map<String, Integer> innerMap = patternSquare
                                                .get(outerType.getKey())
                                                .get(outerSubType.getKey())
                                                .get(innerType.getKey());
                                        int currentValue = innerMap.get(innerSubtype.getKey());

                                        innerMap.put(String.valueOf(innerSubtype.getKey()), currentValue + innerSubtype.getValue());

                                        /*userLogger.info("-------------------");
                                        userLogger.info("current value: " + innerMap.hashCode());
                                        userLogger.info("square: " + innerSubtype.hashCode());
                                        userLogger.info("ph: " + ph.getValue() + nextPh.getValue());
                                        userLogger.info("VERT: " + outerType.getKey() + " " + outerSubType.getKey());
                                        userLogger.info("HOR: " + innerType.getKey() + " " + innerSubtype.getKey());
                                        userLogger.info("-------------------");*/
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}