package phonosemantics.word;

import phonosemantics.data.SoundsBank;
import phonosemantics.language.Language;
import phonosemantics.meaning.Meaning;
import phonosemantics.phonetics.consonant.Consonant;
import phonosemantics.phonetics.phoneme.Phoneme;
import phonosemantics.phonetics.vowel.Vowel;
import phonosemantics.statistics.Statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Word {
    private String word;
    private ArrayList<Phoneme> transcription;
    private Meaning meaning;
    private Language language;
    private int length = 0;
    private PartOfSpeech partOfSpeech;  // TODO think about shifting field to meaning

    public enum PartOfSpeech {
        NOUN, VERB, ADJECTIVE
    }

    public Word(String word, String definition, String language, PartOfSpeech partOfSpeech) {
        this.word = word;
        this.meaning = new Meaning(definition);
        setTranscriptionFromWord(); // length is added here also
        this.language = Language.getLanguage(language);
        this.partOfSpeech = partOfSpeech;
    }

    public Word(String word, Language language) {
        this.word = word;
        this.language = language;
        setTranscriptionFromWord(); // length is added here also
    }

    // for jUnit tests
    public Word(String word) {
        this.word = word;
        setTranscriptionFromWord(); // length is added here also
    }


    /**
     * GETTERS AND SETTERS
     **/
    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public ArrayList<Phoneme> getTranscription() {
        return transcription;
    }

    public void setTranscription(ArrayList<Phoneme> transcription) {
        this.transcription = transcription;
    }

    public int getLength() {
        return length;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }


    public void printTranscription() {
        // Print transcription to console
        System.out.println("Word: " + this.word);
        System.out.print("Transcription: ");
        for (Phoneme p : this.transcription) {
            if (p != null) {
                System.out.print(p.getSymbol() + " ");
            } else {
                System.out.print("_ ");
            }
        }
        System.out.println("Length: " + this.length);
        System.out.println("");
        System.out.println("");
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
            Language language = this.getLanguage();

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
                            if (language != null) {
                                language.categorizePh(ph);
                            }
                        } else {
                            Statistics.addUnknownSymbol(phonemes[i]);
                        }
                        incrementLength();
                    } else {

                        // For 2-graph phoneme
                        Phoneme ph = allPhonemes.get(phonemes[i] + phonemes[i + 1]);
                        if (ph != null) {
                            this.transcription.add(ph);
                            incrementLength();
                            if (language != null) {
                                language.categorizePh(ph);
                            }
                            i++;
                        } else {

                            // For 1-graph phoneme
                            ph = allPhonemes.get(phonemes[i]);
                            if (ph != null) {
                                this.transcription.add(ph);
                                incrementLength();
                                if (language != null) {
                                    language.categorizePh(ph);
                                }
                            } else {
                                // Empty phoneme
                                Statistics.addUnknownSymbol(phonemes[i]);
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
        Statistics.incrementNumOfAllPhonemes();
    }


    /**
     * BUNCH OF METHODS TO COUNT PHONOTYPES
     **/
    public int countPhonotype(Object object) {
        Class objClass = object.getClass();

        if (objClass.equals(SoundsBank.MannerApproximate.class)) {
            return countConsPhonotypeBy(cons -> cons.getMannerApproximate().equals((SoundsBank.MannerApproximate) object));
        } else if (objClass.equals(SoundsBank.MannerPricise.class)) {
//            if (object.equals(Main.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Manner Precise :   ");
//            }
            return countConsPhonotypeBy(cons -> cons.getMannerPricise().equals((SoundsBank.MannerPricise) object));
        } else if (objClass.equals(SoundsBank.Phonation.class)) {
            return countConsPhonotypeBy(cons -> cons.isVoiced().equals((SoundsBank.Phonation) object));
        }

        //* ************************** VOWELS **********************************//
        else if (objClass.equals(SoundsBank.Height.class)) {
//            if (object.equals(Main.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Height :   ");
//            }
            return countVowPhonotypeBy(vow -> vow.getHeight().equals((SoundsBank.Height) object));
        } else if (objClass.equals(SoundsBank.Backness.class)) {
            return countVowPhonotypeBy(vow -> vow.getBackness().equals((SoundsBank.Backness) object));
        } else if (objClass.equals(SoundsBank.Roundness.class)) {
//            if (object.equals(Main.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Roundness :   ");
//            }
            return countVowPhonotypeBy(vow -> vow.isRoundedness().equals((SoundsBank.Roundness) object));
        } else if (objClass.equals(SoundsBank.Nasalization.class)) {
//            if (object.equals(Main.CONSOLE_SHOW_WORDS_OF_CLASS)) {
//                System.out.print("Nasalization :   ");
//            }
            return countVowPhonotypeBy(vow -> vow.isNasalization().equals((SoundsBank.Nasalization) object));
        } else {
            return 0;
        }
    }

    /**
     * The main methods for counting phonotype instances in the word
     **/
    private int countConsPhonotypeBy(Predicate<Consonant> p) {
        int count = 0;
        for (Phoneme ph : this.transcription) {
            if (ph != null) {
                if (ph.getClass().equals(Consonant.class)) {
                    Consonant cons = (Consonant) ph;
                    if (p.test(cons)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private int countVowPhonotypeBy(Predicate<Vowel> p) {
        int count = 0;
        for (Phoneme ph : this.transcription) {
            if (ph != null) {
                if (ph.getClass().equals(Vowel.class)) {
                    Vowel vow = (Vowel) ph;
                    if (p.test(vow)) {
                        count++;
                    }
                }
            } else {
                //System.out.println(this.getWord());
            }
        }
        return count;
    }
}
