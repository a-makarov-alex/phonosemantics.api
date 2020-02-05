package phonosemantics.phonetics;

public class DistinctiveFeatures {
    private MajorClass majorClass;
    private Laryngeal laryngeal;
    //private Manner manner;
    //private Place place;


    public enum MajorClass {
        SYLLABIC, CONSONANTAL, APPROXIMANT, SONORANT
    }

    public enum Laryngeal {
        VOICE, SPREAD_GLOTTIS, CONSTRICTED_GLOTTIS
    }
}
