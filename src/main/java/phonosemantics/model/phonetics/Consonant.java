package phonosemantics.model.phonetics;

import phonosemantics.service.SoundsBank;

public class Consonant extends Phoneme{

    private SoundsBank.PlaceApproximate placeApproximate;
    private SoundsBank.PlacePrecise placePrecise;
    private SoundsBank.MannerApproximate mannerApproximate;
    private SoundsBank.MannerPricise mannerPricise;
    private SoundsBank.Phonation voiced;
    private boolean longCons = false;

    // MANNERS
    // Flags are required because of:
    // Sibilant is also fricative;
    // Affricate can be sibilant and fricative simultaneously, etc.
    private boolean stop = false;
    private boolean affricate = false;
    private boolean fricative = false;
    private boolean sibilant = false;
    private boolean nasal = false;
    private boolean approximant = false;
    private boolean trill = false;
    private boolean flap = false;
    private boolean lateral = false;


    /**
     * CONSTRUCTORS
     * **/
    public Consonant(String symbol, SoundsBank.PlacePrecise placePrecise, SoundsBank.MannerPricise mannerPricise) {
        super(symbol, SoundClass.CONSONANT);
        this.placePrecise = placePrecise;
        this.mannerPricise = mannerPricise;
        this.placeApproximate = placeApproximateFromPrecise(placePrecise);
        this.mannerApproximate = mannerApproximateFromPrecise(mannerPricise);
        this.voiced = SoundsBank.Phonation.DEVOICED;
        // by this point all the flags are marked

    }

    public Consonant(String symbol, SoundsBank.PlacePrecise placePrecise, SoundsBank.MannerPricise mannerPricise, boolean voiced) {
        super(symbol, SoundClass.CONSONANT);
        this.placePrecise = placePrecise;
        this.mannerPricise = mannerPricise;
        this.placeApproximate = placeApproximateFromPrecise(placePrecise);
        this.mannerApproximate = mannerApproximateFromPrecise(mannerPricise);
        if (voiced) {
            this.voiced = SoundsBank.Phonation.VOICED;
        } else {
            this.voiced = SoundsBank.Phonation.DEVOICED;
        }
        // by this point all the flags are marked
    }

    /** Needed for constructor**/
    private SoundsBank.PlaceApproximate placeApproximateFromPrecise(SoundsBank.PlacePrecise placePrecise) {
        switch (placePrecise) {
            case BILABIAL:
            case LABIODENTAL: { return SoundsBank.PlaceApproximate.LABIAL; }
            case DENTAL:
            case ALVEOLAR:
            case POSTALVEOLAR:
            case RETROFLEX: { return SoundsBank.PlaceApproximate.CORONAL; }
            case PALATAL:
            case VELAR:
            case UVULAR: { return SoundsBank.PlaceApproximate.DORSAL; }
            case EPIGLOTTAL:
            case GLOTTAL: { return SoundsBank.PlaceApproximate.LARYNGEAL; }
            default: { return null; }
        }
    }

    /** Needed for constructor**/
    private SoundsBank.MannerApproximate mannerApproximateFromPrecise(SoundsBank.MannerPricise mannerPricise) {
        switch (mannerPricise) {
            case STOP: { this.stop = true; return SoundsBank.MannerApproximate.OBSTRUENT; }
            case AFFRICATE: { this.affricate = true; return SoundsBank.MannerApproximate.OBSTRUENT; }
            case FRICATIVE: { this.fricative = true; return SoundsBank.MannerApproximate.OBSTRUENT; }
            case SIBILANT: { this.fricative = true; this.sibilant = true; return SoundsBank.MannerApproximate.OBSTRUENT; }
            case SIBILANT_AFFRICATE: { this.affricate = true; this.sibilant = true; this.fricative = true; return SoundsBank.MannerApproximate.OBSTRUENT; }
            case NASAL: { this.nasal = true; return SoundsBank.MannerApproximate.SONORANT; }
            case APPROXIMANT: { this.approximant = true; return SoundsBank.MannerApproximate.SONORANT; }
            case TRILL: { this.trill = true; return SoundsBank.MannerApproximate.SONORANT; }
            case FLAP: { this.flap = true; return SoundsBank.MannerApproximate.SONORANT; }
            case LATERAL: { this.lateral = true; return SoundsBank.MannerApproximate.LIQUID; }
            default: {return null; }
        }
    }

    /**
     * GETTERS FOR MAIN FIELDS
     * **/
    public SoundsBank.PlaceApproximate getPlaceApproximate() {
        return placeApproximate;
    }
    public SoundsBank.PlacePrecise getPlacePrecise() {
        return placePrecise;
    }
    public SoundsBank.MannerApproximate getMannerApproximate() {
        return mannerApproximate;
    }
    public SoundsBank.MannerPricise getMannerPricise() {
        return mannerPricise;
    }
    public SoundsBank.Phonation isVoiced() {
        return voiced;
    }
    public boolean isLongCons() {
        return longCons;
    }

    /**
     * GETTERS FOR FLAGS
     * **/
    public boolean isStop() {
        return stop;
    }
    public boolean isAffricate() {
        return affricate;
    }
    public boolean isFricative() {
        return fricative;
    }
    public boolean isSibilant() {
        return sibilant;
    }
    public boolean isNasal() {
        return nasal;
    }
    public boolean isApproximant() {
        return approximant;
    }
    public boolean isTrill() {
        return trill;
    }
    public boolean isFlap() {
        return flap;
    }
    public boolean isLateral() {
        return lateral;
    }
}
