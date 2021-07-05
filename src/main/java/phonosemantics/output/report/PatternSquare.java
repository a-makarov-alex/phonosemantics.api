package phonosemantics.output.report;

import phonosemantics.phonetics.PhonemesBank;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.word.Word;
import phonosemantics.word.wordlist.WordList;

import java.util.HashMap;
import java.util.Map;

public class PatternSquare {
    // Один вордлист - один квадрат. Описание см. в корне проекта в README.

    // Внешний слой: признаки первого звука в паре
    // Средний слой: признаки второго звука в паре
    // Внутренний слой: число обнаруженных паттернов
    private Map<String, Map<String, Map<Object, Integer>>> patternSquare;

    public PatternSquare(WordList wordlist) {
        // нужно взять первое поле первой фонемы - и все поля второй фонемы. проставить в таблицу
        // затем второе поле и опять все поля второй фонемы
        // таким образом идем до конца слова

        // как хранить: хешмапа с хешмапами. Нужно вложить структуру Map<String, Map<Object, Integer>> в ещё одну мапу
        patternSquare = new HashMap<>();
        Map<String, Map<Object, Integer>> outerLayer = DistinctiveFeatures.getFeaturesStructureDraft("all");

        for (Map.Entry<String, Map<Object, Integer>> entry : outerLayer.entrySet()) {
            patternSquare.put(entry.getKey(), DistinctiveFeatures.getFeaturesStructureDraft("all"));
        }
        // На данный момент имеется пустая структура с нулями во всех конечных точках

        PhonemesBank phBank = PhonemesBank.getInstance();
        for (Word w : wordlist.getList()) {
            for (String trPh : w.getTranscription()) {
                PhonemeInTable ph = phBank.find(trPh);
                // тут надо распарсить DistFeatures для фонемы в общую структуру Map<String, Map<Object, Integer>>
                // и идти по ней циклом, тогда не нужно обращаться к рефлексии
            }
        }


    }
}
