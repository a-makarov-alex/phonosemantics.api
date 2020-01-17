package phonosemantics.word.wordlist.wordlistReduced;

import phonosemantics.App;
import phonosemantics.word.wordlist.WordList;

import java.util.ArrayList;

public class WordlistReducedService {

    public static ArrayList<WordListReduced> readAllReducedWordlistsFromInputFile() {
        ArrayList<WordList> allWordLists = App.getAllWordLists();
        ArrayList<WordListReduced> allReducedWordlists = new ArrayList<>();

        for (int i = 0; i < allWordLists.size(); i++) {
            WordListReduced wordListReduced = new WordListReduced(allWordLists.get(i));
            allReducedWordlists.add(wordListReduced);
        }
        return allReducedWordlists;
    }
}
