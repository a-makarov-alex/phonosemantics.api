package phonosemantics.model;

import phonosemantics.model.Meaning;
import phonosemantics.model.phonetics.Phoneme;
import phonosemantics.service.SoundsBank;

import java.util.ArrayList;
import java.util.HashMap;

public class Word {
    private String word;
    private Meaning meaning;
    private ArrayList<Phoneme> transcription;
    private int length = 0;

    public Word(String word, Meaning meaning) {
        this.word = word;
        this.meaning = meaning;
        setTranscriptionFromWord();
    }

    public String getWord() {
        return word;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public ArrayList<Phoneme> getTranscription() {
        return transcription;
    }

    public int getNumOfPhonemes(String phoneme) {
        int count = 0;
        for (int i = 0; i < this.getTranscription().size(); i++) {
            if (this.getTranscription().get(i).getSymbol().equals(phoneme)) {
                count++;
            }
        }
        return count;
    }

    public void setTranscriptionFromWord() {
        this.transcription = new ArrayList<>();
        String word = this.getWord();

        if (word != null) {
            String[] phonemes = word.split("");
            HashMap<String, Phoneme> allPhonemes = SoundsBank.getInstance().getAllPhonemesTable();
//            Language language = this.getLanguage();

            // Phoneme might be a set of 2 symbols.
            // So we need to check the symbol after the current on every step.
            for (int i = 0; i < word.length(); i++) {

                // For extra symbols, accents, tones etc.
                if (!SoundsBank.isExtraSign(phonemes[i])) {

                    // For last symbol
                    if (i == word.length() - 1) {
                        Phoneme ph = allPhonemes.get(phonemes[i]);
                        if (ph != null) {
                            this.transcription.add(ph);
//                            if (language != null) {
//                                language.categorizePh(ph);
//                            }
                        } else {
//                            Statistics.addUnknownSymbol(phonemes[i]);
                        }
                        incrementLength();
                    } else {

                        // For 2-graph phoneme
                        Phoneme ph = allPhonemes.get(phonemes[i] + phonemes[i + 1]);
                        if (ph != null) {
                            this.transcription.add(ph);
                            incrementLength();
//                            if (language != null) {
//                                language.categorizePh(ph);
//                            }
                            i++;
                        } else {

                            // For 1-graph phoneme
                            ph = allPhonemes.get(phonemes[i]);
                            if (ph != null) {
                                this.transcription.add(ph);
                                incrementLength();
//                                if (language != null) {
//                                    language.categorizePh(ph);
//                                }
                            } else {
                                // Empty phoneme
//                                Statistics.addUnknownSymbol(phonemes[i]);
                                incrementLength();
                            }
                        }
                    }
                } else {
//                    if (Main.CONSOLE_EXTRA_SYMBOLS) {
//                        System.out.println("Extra: " + phonemes[i]);
//                    }
                }
            }
//            if (Main.CONSOLE_SHOW_TRASCRIPTION) {
//                printTranscription();
//            }
        }
    }

    // Увеличивает как счетчик длины конкретного слова, так и сумму ВСЕХ фонем в исследовании
    private void incrementLength() {
        this.length += 1;
//        Statistics.incrementNumOfAllPhonemes();
    }
}
