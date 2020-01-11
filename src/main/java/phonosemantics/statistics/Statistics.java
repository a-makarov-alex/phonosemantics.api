package phonosemantics.statistics;

import java.util.*;

public class Statistics {

    // kinds of calculations
    public enum KindOfStats {
        WORDS_WITH_PHTYPE_PER_LIST,
        PHTYPES_PER_LIST,
        PHTYPES_AVERAGE_PER_WORD
    }


    private static HashMap<String, Integer> unknownPhonemes = new HashMap<>();

    private static int numOfAllPhonemes = 0;

    public static HashMap<String, Integer> getUnknownPhonemes() {
        return unknownPhonemes;
    }

    public static int getNumOfAllPhonemes() {
        return numOfAllPhonemes;
    }

    // Сумма всех неизвестных символов в исследовании
    public static int getNumOfUnknownPhonemes() {
        int count = 0;
        for (Map.Entry<String, Integer> elem : unknownPhonemes.entrySet()) {
            count += elem.getValue();
        }
        return count;
    }

    // Добавляет новый неизвестный символ или инкрементит его количество, если он уже существует в hashmap
    public static void addUnknownSymbol(String symbol) {
        if (!unknownPhonemes.containsKey(symbol)) {
            unknownPhonemes.put(symbol, 1);
        } else {
            unknownPhonemes.put(symbol, unknownPhonemes.get(symbol) + 1);
        }
    }


    // Увеличивает счетчик всех фонем в исследовании.
    // Инкрементится для ФОНЕМЫ (1 или 2 графемы) и для НЕИЗВЕСТНЫХ знаков
    public static void incrementNumOfAllPhonemes() {
        numOfAllPhonemes ++;
    }

    // Wordlists are added here with semantics as a key
    // TODO: create a Semantics class
    // Remember that Word has Semantics field. No need of HashMap
    //private WordList globalWordlist = Main.getInputFile().composeWordList("Big"); //TODO: =getGlobalWordlist() в классе InputFile


    /** GETTERS AND SETTERS **/
    /*public WordList getGlobalWordlist() {
        return globalWordlist;
    }*/
}
