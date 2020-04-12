package phonosemantics.phonetics;

import org.springframework.web.bind.annotation.*;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PhonemesBankController {
    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes")
    public ArrayList<PhonemeInTable> getAllPhonemes() {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getPhonemesListForTableUI();
        return list;
    }

    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/{phoneme}")
    public PhonemeInTable getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        ArrayList<PhonemeInTable> list = PhonemesBank.getInstance().getPhonemesListForTableUI();
        for (PhonemeInTable ph : list) {
            if (ph.getValue().equals(phoneme.toLowerCase())) {
                return ph;
            }
        }
        return null;
    }

    /**
     * GETTING UNKNOWN PHONEMES
     * ugly path, but phonemes/{phoneme} is already taken
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/unknown/all")
    public HashMap<String, Integer> getUnknownPhonemes() {
        return Statistics.getUnknownPhonemes();
    }

    /**
     * GETTING PHONEMES LIST BY DISTINCTIVE FEATURE
     * **/
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/features/{feature}")
    public ArrayList<String> getPhonemesWithDistFeature(
            @PathVariable(value="feature") String feature,
            @RequestParam(value="featureValue") String featureValue
    ) {
        feature = feature.toLowerCase();
        featureValue = featureValue.toLowerCase();

        HashMap<String, PhonemeInTable> phMap = PhonemesBank.getInstance().getAllPhonemes();
        ArrayList<String> resultList = new ArrayList<>();

        for (Map.Entry<String, PhonemeInTable> entry : phMap.entrySet()) {
            DistinctiveFeatures df = entry.getValue().getDistinctiveFeatures();
            if (df != null) {

                Field[] fields = df.getClass().getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    // Cycle over MajorClass / Place / Manner / VowelSpace
                    try {
                        if (!fields[i].isAccessible()) {
                            fields[i].setAccessible(true);
                        }

                        // Cycle over vocoid / approximant / height / backness ...
                        if (fields[i].get(df) != null) {
                            Field[] innerFields = fields[i].get(df).getClass().getDeclaredFields();

                            for (int j = 0; j < innerFields.length; j++) {
                                if (!innerFields[j].isAccessible()) {
                                    innerFields[j].setAccessible(true);
                                }
                                Object o = innerFields[j].get(fields[i].get(df));
                                String fieldName = String.valueOf(innerFields[j].getName()).toLowerCase();
                                if (feature.equals(fieldName)) {
                                    //System.out.println("SUCCESS FIELD NAME: " + fieldName);

                                    if (featureValue.equals(String.valueOf(o).toLowerCase())) {
                                        //System.out.println("SUCCESS: " + String.valueOf(o));
                                        resultList.add(entry.getKey());
                                    }
                                }
                            }
                        } else {
                            //System.out.println("smth is null: " + fields[i].getName());
                        }
                    } catch (IllegalAccessException e) {
                        //System.out.println("Exception: liiegal access: ");
                        return null;
                    }
                }
            } else {
                //System.out.println("DF is null: " + entry.getKey());
            }
        }
        return resultList;
    }


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/test/new")
    public ArrayList<Object> getPhonemesTestNew2() {
            HashMap<String, PhonemeInTable> phMap = PhonemesBank.getInstance().getAllPhonemes();
            ArrayList<Object> resultList = new ArrayList<>();

            for (Map.Entry<String, PhonemeInTable> entry : phMap.entrySet()) {
                DistinctiveFeatures df = entry.getValue().getDistinctiveFeatures();
                if (df != null) {

                    Field[] fields = df.getClass().getDeclaredFields();

                    for (int i = 0; i < fields.length; i++) {
                        // Cycle over MajorClass / Place / Manner / VowelSpace
                        try {
                            if (!fields[i].isAccessible()) {
                                fields[i].setAccessible(true);
                            }

                            // Cycle over vocoid / approximant / height / backness ...
                            if (fields[i].get(df) != null) {
                                Field[] innerFields = fields[i].get(df).getClass().getDeclaredFields();

                                for (int j = 0; j < innerFields.length; j++) {
                                    if (!innerFields[j].isAccessible()) {
                                        innerFields[j].setAccessible(true);
                                    }

                                    resultList.add(innerFields[j].get(fields[i].get(df)));
                                }
                            } else {
                                System.out.println("smth is null: " + fields[i].getName());
                            }
                        } catch (IllegalAccessException e) {
                            System.out.println("Exception: liiegal access: ");
                            return null;
                        }
                    }
                } else {
                    System.out.println("DF is null: " + entry.getKey());
                }
            }
        return resultList;
    }





/*
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/phonemes/test/new")
    public Object getPhonemesTestNew() {
        PhonemeInTable ph = PhonemesBank.getInstance().find("a");
        DistinctiveFeatures df = ph.getDistinctiveFeatures();

        Field[] fields = df.getClass().getDeclaredFields();

        try {
            if (!fields[0].isAccessible()) {
                System.out.println("fuck_1");
                fields[0].setAccessible(true);
            }
            Field[] innerFields = fields[0].get(df).getClass().getDeclaredFields();

            if (!innerFields[0].isAccessible()) {
                System.out.println("fuck_2");
                innerFields[0].setAccessible(true);
            }
            return innerFields[0].get(fields[0].get(df));

        } catch(IllegalAccessException e) {
            return "FUCK";
        }
    }
*/
}
