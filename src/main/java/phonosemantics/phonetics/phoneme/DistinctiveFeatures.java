package phonosemantics.phonetics.phoneme;

import phonosemantics.phonetics.phoneme.distinctiveFeatures.MannerPrecise;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.Stricture;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.consonants.*;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Backness;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Height;
import phonosemantics.phonetics.phoneme.distinctiveFeatures.vowels.Roundness;

import java.util.ArrayList;
import java.util.HashMap;

public class DistinctiveFeatures {
    private MajorClass majorClass;
    private Manner manner;
    private Place place; //consonants only
    private VowelSpace vowelSpace; //vowels only
    //    private Laryngeal laryngeal;

    private static HashMap<Object, Integer> allFeaturesMap = null;


    /** CONSTRUCTOR FOR CONSONANTS **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, boolean voiced, PlacePrecise placePrecise) {
        if (mannerPrecise != MannerPrecise.APPROXIMANT) {
            this.majorClass = new MajorClass(false,false);
        } else {
            this.majorClass = new MajorClass(false,true);
        }
        this.manner = new Manner(mannerPrecise, voiced);
        this.place = new Place(placePrecise);
        this.vowelSpace = new VowelSpace(); //all fields = NOT_APPLICABLE
    }

    /** CONSTRUCTOR FOR STRIDENT FRICATIVES & AFFRICATES **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, boolean voiced, PlacePrecise placePrecise, Sibilant sibilant) {
        if (mannerPrecise != MannerPrecise.APPROXIMANT) {
            this.majorClass = new MajorClass(false,false);
        } else {
            this.majorClass = new MajorClass(false,true);
        }
        this.manner = new Manner(mannerPrecise, voiced, sibilant);
        this.place = new Place(placePrecise);
        this.vowelSpace = new VowelSpace(); //all fields = NOT_APPLICABLE
    }

    /** CONSTRUCTOR FOR LATERAL & RHOTICS **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, boolean voiced, PlacePrecise placePrecise, Lateral lateral, Rhotics rhotics) {
        if (lateral == Lateral.LATERAL || rhotics == Rhotics.RHOTICS) {
            this.majorClass = new MajorClass(false,true);
        } else {
            // TODO: this is incorrect!!!!
            this.majorClass = new MajorClass(false,false);
        }
        this.manner = new Manner(mannerPrecise, voiced, lateral, rhotics);
        this.place = new Place(placePrecise);
        this.vowelSpace = new VowelSpace(); //all fields = NOT_APPLICABLE

        if (mannerPrecise == MannerPrecise.FRICATIVE && rhotics == Rhotics.RHOTICS) {
            this.manner.strident = Strident.STRIDENT;
            this.manner.sibilant = Sibilant.NOT_SIBILANT;
        }
    }

    /** CONSTRUCTOR FOR SEMIVOWELS **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, PlacePrecise placePrecise, Semivowel semivowel) {
        this.majorClass = new MajorClass(true,true);
        this.manner = new Manner(mannerPrecise, semivowel);
        this.place = new Place(placePrecise);
        this.vowelSpace = new VowelSpace(); //all fields = NOT_APPLICABLE
    }

    /** CONSTRUCTOR FOR VOWELS **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, Height height, Backness backness, Roundness roundness) {
        this.majorClass = new MajorClass(true,true);
        this.manner = new Manner(mannerPrecise, true);
        this.place = new Place(); //all fields = NOT_APPLICABLE
        this.vowelSpace = new VowelSpace(height, backness, roundness);
    }

    /** CONSTRUCTOR FOR NASALIZED VOWELS **/
    public DistinctiveFeatures(MannerPrecise mannerPrecise, Height height, Backness backness, Roundness roundness, boolean nasalization) {
        this.majorClass = new MajorClass(true,true);
        this.manner = new Manner(mannerPrecise, true);
        this.manner.nasal = nasalization;
        this.place = new Place(); //all fields = NOT_APPLICABLE
        this.vowelSpace = new VowelSpace(height, backness, roundness);
    }

    class MajorClass {
        // TODO: свойства вычисляются из manner precise
        //private boolean syllabic; can not be done cause depends on context (consonants)

        //opposition: vocoid-contoid.
        //obstruents, nasals, liquids, and trills. Vowels, glides and laryngeal segments are not consonantal.
        //TODO: узнать больше о выделении класса ларингальных!
        private boolean vocoid;

        // approx - с точки зрения фонологии (phonology) (!)
        // TODO: выяснить подробности про аппроксиманты
        private boolean approximant = false; //Approximant segments include vowels, glides, and liquids while excluding nasals and obstruents.

        public MajorClass(boolean vocoid) {
            this.vocoid = vocoid;
        }

        public MajorClass(boolean vocoid, boolean approximant) {
            this.vocoid = vocoid;
            this.approximant = approximant;
        }

        public boolean isVocoid() {
            return vocoid;
        }

        public boolean isApproximant() {
            return approximant;
        }
    }

    // TODO: investigate situation with laryngeals
//    class Laryngeal {
//        private boolean voice; //voiced-devoiced
//        private boolean spreadGlottis = false;
//        private boolean constrictedGlottis = false;
//    }

    class Manner {
        private MannerPrecise mannerPrecise;
        // all fields:
        // * sonorant
        // * continuant
        // * Stricture stricture
        // * nasal
        // * voiced
        // strident <--- fric+affric OR obstruents
        // sibilant <--- strident
        // lateral
        // rhotics
        // semivowel <--- approximants

        // opposition: sonorant-obstruent. applied to all.
        // (+) vowels, approximates, nasals, liquids. (-) stops, affricates, fricatives.
        private boolean sonorant = false;

        // opposition: continuant-occlusive. applied to all.
        // (+) vowels, approximates, liquids, fricatives. (-) plosives, affricates, nasals.
        // ***uncertain: lateral fric, lateral approx, tap/flap
        private boolean continuant = false;

        // Shows degree of airflow. applied to all.
        // TODO: вычисляемое свойство
        private Stricture stricture = Stricture.NOT_SELECTED;

        // opposition: nasal-oral. applied to all.
        // (+)nasal vowels, nasal consonants
        // TODO: need to clarify if LARYNGEAL is the third option. (larynx, pharynx)
        private boolean nasal = false;

        // opposition: voiced-devoiced
        // TODO: if it's a right place to write this field?
        private boolean voiced = false;

        // parameter appliable to affricates and fricatives (or maybe obstruents at all).
        // TODO: check it out!
        private Strident strident = Strident.NOT_APPLICABLE;

        // parameter appliable to stridents only
        // TODO: check it out!
        private Sibilant sibilant = Sibilant.NOT_APPLICABLE;

        // lateral and rhotics may combine with other manners
        // TODO: check if it's right!
        // opposition: lateral-central
        private Lateral lateral = Lateral.NOT_APPLICABLE;

        private Rhotics rhotics = Rhotics.NOT_APPLICABLE;

        // appliable to approximants
        private Semivowel semivowel = Semivowel.NOT_APPLICABLE;

        // TODO: is it a good idea?
        //private boolean delayedRelease = false; // distinguish affricates (+) from stops (-)

        /** CONSTRUCTOR FOR VOWELS, PLOSIVES, NASALS, NOT_STRIDENT FRICATIVES & AFFRICATES, TRILLS, TAP-FLAPS**/
        public Manner(MannerPrecise mannerPrecise, boolean voiced) {
            this.mannerPrecise = mannerPrecise;
            this.voiced = voiced;
            this.countMannerParameters(mannerPrecise);
            if (mannerPrecise == MannerPrecise.AFFRICATE || mannerPrecise == MannerPrecise.FRICATIVE) {
                this.strident = Strident.NOT_STRIDENT;
                this.sibilant = Sibilant.NOT_SIBILANT;
            }
            // TODO: учесть исключения
            if (mannerPrecise == MannerPrecise.TRILL || mannerPrecise == MannerPrecise.TAP_FLAP) {
                this.rhotics = Rhotics.RHOTICS;
            }
        }

        /** CONSTRUCTOR FOR STRIDENT FRICATIVES & AFFRICATES**/
        public Manner(MannerPrecise mannerPrecise, boolean voiced, Sibilant sibilant) {
            this.mannerPrecise = mannerPrecise;
            this.voiced = voiced;
            this.strident = Strident.STRIDENT;
            this.sibilant = sibilant;
            this.countMannerParameters(mannerPrecise);
        }

        /** CONSTRUCTOR FOR LATERALS AND RHOTICS**/
        public Manner(MannerPrecise mannerPrecise, boolean voiced, Lateral lateral, Rhotics rhotics) {
            this.mannerPrecise = mannerPrecise;
            this.sonorant = true;
            this.continuant = true;
            this.voiced = voiced;
            this.lateral = lateral;
            this.rhotics = rhotics;
            this.countMannerParameters(mannerPrecise);
        }

        /** CONSTRUCTOR FOR SEMIVOWELS **/
        public Manner(MannerPrecise mannerPrecise, Semivowel semivowel) {
            this.mannerPrecise = mannerPrecise;
            this.voiced = true;
            this.semivowel = semivowel;
            this.countMannerParameters(mannerPrecise);
        }

        public void countMannerParameters(MannerPrecise mannerPrecise) {
            switch(mannerPrecise) {
                case VOWEL: {
                    sonorant = true;
                    continuant = true;
                    stricture = Stricture.LOWEST;
                    break;
                }
                case APPROXIMANT: {
                    sonorant = true;
                    continuant = true;
                    stricture = Stricture.LOW_MID;
                    break;
                }
                case PLOSIVE: {
                    stricture = Stricture.HIGH;
                    break;
                }
                case AFFRICATE: {
                    stricture = Stricture.MID_HIGH;
                    break;
                }
                case FRICATIVE: {
                    stricture = Stricture.MID;
                    continuant = true;
                    break;
                }
                case NASAL: {
                    sonorant = true;
                    nasal = true;
                    break;
                }
            }
        }

        public void setLateral(Lateral lateral) {
            this.lateral = lateral;
        }

        public void setRhotics(Rhotics rhotics) {
            this.rhotics = rhotics;
        }

        public boolean isSonorant() {
            return sonorant;
        }

        public boolean isContinuant() {
            return continuant;
        }

        public Stricture getStricture() {
            return stricture;
        }

        public boolean isNasal() {
            return nasal;
        }

        public boolean isVoiced() {
            return voiced;
        }

        public Strident getStrident() {
            return strident;
        }

        public Sibilant getSibilant() {
            return sibilant;
        }

        public Lateral getLateral() {
            return lateral;
        }

        public Rhotics getRhotics() {
            return rhotics;
        }

        public MannerPrecise getMannerPrecise() {
            return mannerPrecise;
        }

        public Semivowel getSemivowel() {
            return semivowel;
        }
    }

    class Place {
        private PlaceApproximate approximate;
        private PlacePrecise precise;

        public Place(PlacePrecise precise) {
            this.precise = precise;
            this.approximate = placeApproximateFromPrecise(precise);
        }

        // Constructor for vowels
        public Place() {
            this.approximate = PlaceApproximate.NOT_APPLICABLE;
            this.precise = PlacePrecise.NOT_APPLICABLE;
        }

        public PlaceApproximate getApproximate() {
            return approximate;
        }

        public PlacePrecise getPrecise() {
            return precise;
        }

        /** Needed for constructor**/
        private PlaceApproximate placeApproximateFromPrecise(PlacePrecise placePrecise) {
            switch (placePrecise) {
                case BILABIAL:
                case LABIODENTAL: { return PlaceApproximate.LABIAL; }
                case DENTAL:
                case ALVEOLAR:
                case POSTALVEOLAR:
                case RETROFLEX: { return PlaceApproximate.CORONAL; }
                case PALATAL:
                case VELAR:
                case UVULAR: { return PlaceApproximate.DORSAL; }
                case EPIGLOTTAL:
                case GLOTTAL: { return PlaceApproximate.LARYNGEAL; }
                default: { return PlaceApproximate.NOT_APPLICABLE; }
            }
        }
    }

    class VowelSpace {
        private Height height;
        private Backness backness;
        private Roundness roundness;

        public VowelSpace(Height height, Backness backness, Roundness roundness) {
            this.height = height;
            this.backness = backness;
            this.roundness = roundness;
        }

        //constructor for consonants
        public VowelSpace() {
            this.height = Height.NOT_APPLICABLE;
            this.backness = Backness.NOT_APPLICABLE;
            this.roundness = Roundness.NOT_APPLICABLE;
        }

        public Height getHeight() {
            return height;
        }

        public Backness getBackness() {
            return backness;
        }

        public Roundness getRoundness() {
            return roundness;
        }
    }

    // Создает MAP со всеми фонотипами в качестве ключей. Значения нулевые.
//    public static HashMap<Object, Integer> getAllFeatures() {
//        if (allFeaturesMap != null) {
//            return allFeaturesMap;
//
//        } else {
//            HashMap<Object, Integer> map = new HashMap<>();
//
//            /* **********************  VOWELS ***************************/
//            // Height
//            Object[] hArr = Height.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Backness
//            hArr = Backness.values();
//            for (int i = 0; i < hArr.length; i++) {
//                map.put(hArr[i], 0);
//            }
//
//            // Roundness
//            hArr = Roundness.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Nasalization
//            hArr = Nasalization.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            /* **********************  CONSONANTS ***************************/
//            // Cons.MannerApprox
//            hArr = MannerApproximate.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Cons.MannerPrecise
//            hArr = MannerPricise.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Cons.PlaceApprox
//            hArr = PlaceApproximate.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Cons.PlacePrecise
//            hArr = PlacePrecise.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            // Phonation
//            hArr = Phonation.values();
//            for (Object ob : hArr) {
//                map.put(ob, 0);
//            }
//
//            userLogger.debug("map of phonotypes is created");
//            allPhTypesMap = map;
//            return map;
//        }
//    }

    public static HashMap<String, Object[]> getAllFeaturesForAPI() {

        //TODO: переписать через вызов такого же метода для general/vowel/consonant
        HashMap<String, Object[]> map = new HashMap<>();

        map.put("Manner", MannerPrecise.values());
        map.put("Stricture", Stricture.values());
        map.put("Vocoid", new String[] {"true", "false"});
        map.put("Approximate", new String[] {"true", "false"});
        map.put("Sonorant", new String[] {"true", "false"});
        map.put("Continuant", new String[] {"true", "false"});
        map.put("Nasal", new String[] {"true", "false"});


        /* **********************  VOWELS ***************************/
        map.put("Height", Height.values());
        map.put("Backness", Backness.values());
        map.put("Roundness", Roundness.values());

        /* **********************  CONSONANTS ***************************/
        map.put("PlaceApproximate", PlaceApproximate.values());
        map.put("PlacePrecise", PlacePrecise.values());
        map.put("Strident", Strident.values());
        map.put("Sibilant", Sibilant.values());
        map.put("Semivowel", Semivowel.values());
        map.put("Lateral", Lateral.values());
        map.put("Rhotics", Rhotics.values());
        map.put("Voiced", new String[] {"true", "false"});

        return map;
    }

    public static HashMap<String, Object[]> getGeneralFeaturesForAPI() {
        HashMap<String, Object[]> map = new HashMap<>();

        map.put("Manner", MannerPrecise.values());
        map.put("Stricture", Stricture.values());
        map.put("Vocoid", new String[] {"true", "false"});
        map.put("Approximate", new String[] {"true", "false"});
        map.put("Sonorant", new String[] {"true", "false"});
        map.put("Continuant", new String[] {"true", "false"});
        map.put("Nasal", new String[] {"true", "false"});

        return map;
    }

    public static HashMap<String, Object[]> getVowelFeaturesForAPI() {
        HashMap<String, Object[]> map = new HashMap<>();

        /* **********************  VOWELS ***************************/
        map.put("Height", Height.values());
        map.put("Backness", Backness.values());
        map.put("Roundness", Roundness.values());

        return map;
    }

    public static HashMap<String, Object[]> getConsonantFeaturesForAPI() {
        HashMap<String, Object[]> map = new HashMap<>();

        /* **********************  CONSONANTS ***************************/
        map.put("PlaceApproximate", PlaceApproximate.values());
        map.put("PlacePrecise", PlacePrecise.values());
        map.put("Strident", Strident.values());
        map.put("Sibilant", Sibilant.values());
        map.put("Semivowel", Semivowel.values());
        map.put("Lateral", Lateral.values());
        map.put("Rhotics", Rhotics.values());
        map.put("Voiced", new String[] {"true", "false"});

        return map;
    }

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public Manner getManner() {
        return manner;
    }

    public Place getPlace() {
        return place;
    }

    public VowelSpace getVowelSpace() {
        return vowelSpace;
    }
}
