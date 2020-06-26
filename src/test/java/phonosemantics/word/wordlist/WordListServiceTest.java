package phonosemantics.word.wordlist;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WordListServiceTest {

    @Test
    public void getWordlist() {
    }

    @Test
    public void getAllWordlists() {
        ArrayList<WordList> allWls = WordListService.getAllWordLists();
        assert allWls != null;
        assert allWls.size() != 0;
        // TODO: check if the num of WLs equals the num of meanings in the input file
    }
}