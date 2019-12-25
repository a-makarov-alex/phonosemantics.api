package phonosemantics.service;

import phonosemantics.model.Meaning;
import phonosemantics.model.Word;
import phonosemantics.model.phonetics.Phoneme;

import java.util.ArrayList;
import java.util.HashMap;

public class WordService {
    private static ArrayList<Word> allwords;

    public static ArrayList<Word> getAllwords() {
        allwords = new ArrayList<>();
        allwords.add(new Word("one", new Meaning("one")));
        allwords.add(new Word("two", new Meaning("two")));
        allwords.add(new Word("three", new Meaning("one")));
        allwords.add(new Word("four", new Meaning("two")));
        return allwords;
    }

}
