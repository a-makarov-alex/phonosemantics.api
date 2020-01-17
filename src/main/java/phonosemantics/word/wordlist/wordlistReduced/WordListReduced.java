package phonosemantics.word.wordlist.wordlistReduced;

import phonosemantics.word.wordlist.WordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordListReduced {
    private String meaning;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<Object, WordList.PhTypeStats> phTypeStatsMap = new HashMap<>();
    //private ArrayList<WordList.PhTypeStats> phTypeStatsList = new ArrayList<>();

    public WordListReduced(WordList wordlist) {
        this.meaning = wordlist.getMeaning();
        for (int i = 0; i < wordlist.getList().size(); i++) {
            this.list.add(wordlist.getList().get(i).getWord());
        }
        this.phTypeStatsMap = wordlist.getPhTypeStatsMap();
        //TODO: Почему не работает с листом??!
        /*for (Map.Entry<Object, WordList.PhTypeStats> phTypeStats : wordlist.getPhTypeStatsMap().entrySet()) {
            this.phTypeStatsList.add(phTypeStats.getValue());
        }*/
    }

    public String getMeaning() {
        return meaning;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public HashMap<Object, WordList.PhTypeStats> getPhTypeStatsMap() {
        return phTypeStatsMap;
    }
}
