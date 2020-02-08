package phonosemantics.phonetics;

public class DistinctiveFeatures {
    private MajorClass majorClass;
//    private Laryngeal laryngeal;
    private Manner manner;
    private PlaceApproximate placeApproximate; //consonants only
    private PlacePrecise placePrecise; //consonants only
    private Height height;  //vowels only
    private Backness backness; //vowels only
    private boolean roundness; //vowels only

    class MajorClass {
        //private boolean syllabic; can not be done cause depends on context (consonants)

        //opposition: vocoid-contoid.
        //obstruents, nasals, liquids, and trills. Vowels, glides and laryngeal segments are not consonantal.
        //TODO: узнать больше о выделении ларингальных!
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

//    class Laryngeal {
//        private boolean voice; //voiced-devoiced
//        private boolean spreadGlottis = false;
//        private boolean constrictedGlottis = false;
//    }

    class Manner {
        private MannerPrecise mannerPrecise;

        // opposition: sonorant-obstruent. applied to all.
        // (+) vowels, approximates, nasals, liquids. (-) stops, affricates, fricatives.
        private boolean sonorant;

        // opposition: continuant-occlusive. applied to all.
        // (+) vowels, approximates, liquids, fricatives. (-) plosives, affricates, nasals.
        // ***uncertain: lateral fric, lateral approx, tap/flap
        private boolean continuant;

        // Shows degree of airflow. applied to all.
        private Stricture stricture;

        // opposition: nasal-oral. applied to all.
        // TODO: need to clarify if LARYNGEAL is the third option. (larynx, pharynx)
        private boolean nasal = false; //nasal vowels, nasal consonants (-)oral

        // opposition: voiced-devoiced
        // TODO: if it's a right place to write this field?
        private boolean voiced;

        // parameter appliable to affricates and fricatives (or maybe obstruents at all).
        // TODO: check it out!
        private Strident strident = Strident.NOT_APPLIABLE;

        // parameter appliable to stridents only
        // TODO: check it out!
        private Sibilant sibilant = Sibilant.NOT_APPLIABLE;

        // lateral and rhotics may combine with other manners
        // TODO: check if it's right!
        // opposition: lateral-central
        private Lateral lateral = Lateral.NOT_APPLIABLE;

        private Rhotics rhotics = Rhotics.NOT_APPLIABLE;

        // appliable to approximants
        private Semivowel semivowel = Semivowel.NOT_APPLIABLE;

        // TODO: is it a good idea?
        //private boolean delayedRelease = false; // distinguish affricates (+) from stops (-)


        public Manner(MannerPrecise mannerPrecise, boolean sonorant, boolean continuant, Stricture stricture, boolean nasal, boolean voiced) {
            this.mannerPrecise = mannerPrecise;

            this.sonorant = sonorant;
            this.continuant = continuant;
            this.stricture = stricture;
            this.nasal = nasal;
            this.voiced = voiced;
        }

        public void setStrident(Strident strident) {
            this.strident = strident;
        }

        public void setSibilant(Sibilant sibilant) {
            this.sibilant = sibilant;
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
    }

    public enum PlaceApproximate {
        LABIAL, CORONAL, DORSAL, LARYNGEAL, NONE
    }

    public enum PlacePrecise {
        BILABIAL, LABIODENTAL,                      // LABIAL
        DENTAL, ALVEOLAR, POSTALVEOLAR, RETROFLEX,  // CORONAL
        PALATAL, VELAR, UVULAR,                     // DORSAL
        EPIGLOTTAL, GLOTTAL,
        NONE                                        // VOWELS
    }

    public enum Height {
        OPEN, NEAR_OPEN, OPEN_MID, MID, CLOSE_MID, NEAR_CLOSE, CLOSE, NONE
    }

    public enum Backness {
        BACK, CENTRAL, FRONT, NONE
    }

    public enum Stricture {
        LOWEST,     //vowels
        LOW_MID,    //approximants
        MID,        //fricatives
        MID_HIGH,   //affricates
        HIGH        //plosives
    }

    public enum Strident {
        STRIDENT, NOT_STRIDENT, NOT_APPLIABLE
    }

    public enum Sibilant {
        SIBILANT, NOT_SIBILANT, NOT_APPLIABLE
    }

    public enum Lateral {
        LATERAL, NOT_LATERAL, NOT_APPLIABLE
    }

    public enum Rhotics {
        RHOTICS, NOT_RHOTICS, NOT_APPLIABLE
    }

    public enum Semivowel {
        SEMIVOWEL, NOT_SEMIVOWEL, NOT_APPLIABLE
    }

    public enum MannerPrecise {
        PLOSIVE, AFFRICATE, FRICATIVE,
        VOWEL, APPROXIMANT, NASAL,
        TAP_FLAP, TRILL     //vibrants. NB! Liquids (laterals and rhotics) are specified as additional manners. Check Manners class.
    }

    /**Constructor for consonants**/
    public DistinctiveFeatures(MajorClass majorClass, Manner manner, PlacePrecise placePrecise) {
        this.majorClass = majorClass;
        this.manner = manner;
        this.placePrecise = placePrecise;
        this.placeApproximate = placeApproximateFromPrecise(placePrecise);
        this.backness = Backness.NONE;
        this.height = Height.NONE;
        this.roundness = false;
    }

    public DistinctiveFeatures() {

    }

    /**Constructor for vowels**/
    public DistinctiveFeatures(MajorClass majorClass, Manner manner, Height height, Backness backness, boolean roundness) {
        this.majorClass = majorClass;
        this.manner = manner;
        this.height = height;
        this.backness = backness;
        this.roundness = roundness;
        this.placeApproximate = PlaceApproximate.NONE;
        this.placePrecise = PlacePrecise.NONE;
    }

    public MajorClass getMajorClass() {
        return majorClass;
    }

    public Manner getManner() {
        return manner;
    }

    public PlaceApproximate getPlaceApproximate() {
        return placeApproximate;
    }

    public PlacePrecise getPlacePrecise() {
        return placePrecise;
    }

    public Height getHeight() {
        return height;
    }

    public Backness getBackness() {
        return backness;
    }

    public boolean isRoundness() {
        return roundness;
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
            default: { return PlaceApproximate.NONE; }
        }
    }
}
