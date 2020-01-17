package phonosemantics.word.wordlist.wordlistReduced;

import phonosemantics.word.Word;
import phonosemantics.word.WordReduced;
import phonosemantics.word.wordlist.WordList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordListReduced {
    private String meaning;
    private ArrayList<WordReduced> list = new ArrayList<>();
    //private HashMap<Object, WordList.PhTypeStats> phTypeStatsMap = new HashMap<>(); //remove later
    private ArrayList<WordList.PhTypeStats> phTypeStatsList = new ArrayList<>();

    public WordListReduced(WordList wordlist) {
        ArrayList<Word> wl = wordlist.getList();

        this.meaning = wordlist.getMeaning();
        for (int i = 0; i < wl.size(); i++) {
            this.list.add(new WordReduced(wl.get(i)));
        }
        //this.phTypeStatsMap = wordlist.getPhTypeStatsMap(); //remove later
        for (Map.Entry<Object, WordList.PhTypeStats> phTypeStats : wordlist.getPhTypeStatsMap().entrySet()) {
            this.phTypeStatsList.add(phTypeStats.getValue());
        }
    }

    public String getMeaning() {
        return meaning;
    }

    public ArrayList<WordReduced> getList() {
        return list;
    }

    //remove later
//    public HashMap<Object, WordList.PhTypeStats> getPhTypeStatsMap() {
//        return phTypeStatsMap;
//    }


    public ArrayList<WordList.PhTypeStats> getPhTypeStatsList() {
        return phTypeStatsList;
    }
}
