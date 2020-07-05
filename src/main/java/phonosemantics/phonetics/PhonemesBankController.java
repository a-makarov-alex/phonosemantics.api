package phonosemantics.phonetics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import phonosemantics.data.PortConfig;
import phonosemantics.phonetics.phoneme.DistinctiveFeatures;
import phonosemantics.phonetics.phoneme.PhonemeInTable;
import phonosemantics.statistics.Statistics;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PhonemesBankController {
    private static final Logger userLogger = LogManager.getLogger(PhonemesBankController.class);

    /**
     * GETTING ALL PHONEMES
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/phonemes")
    public List<PhonemeInTable> getAllPhonemes() {
        List<PhonemeInTable> list = PhonemesBank.getInstance().getAllPhonemesList();
        return list;
    }

    /**
     * GETTING PHONEME BY ITS TITLE
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/phonemes/{phoneme}")
    public PhonemeInTable getPhonemeByName(@PathVariable(value="phoneme") String phoneme) {
        List<PhonemeInTable> list = PhonemesBank.getInstance().getAllPhonemesList();
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
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/phonemes/unknown/all")
    public Map<String, Integer> getUnknownPhonemes() {
        return Statistics.getUnknownPhonemes();
    }

    /**
     * GETTING PHONEMES LIST BY DISTINCTIVE FEATURE
     * **/
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/phonemes/features/{feature}")
    public List<String> getPhonemesWithDistFeature(
            @PathVariable(value="feature") String feature,
            @RequestParam(value="featureValue") String featureValue
    ) {
        feature = feature.toLowerCase();
        featureValue = featureValue.toLowerCase();

        Map<String, PhonemeInTable> phMap = PhonemesBank.getInstance().getAllPhonemes();
        List<String> resultList = new ArrayList<>();

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
                                    //userLogger.info("SUCCESS FIELD NAME: " + fieldName);

                                    if (featureValue.equals(String.valueOf(o).toLowerCase())) {
                                        //userLogger.info("SUCCESS: " + String.valueOf(o));
                                        resultList.add(entry.getKey());
                                    }
                                }
                            }
                        } else {
                            userLogger.info("smth is null: " + fields[i].getName());
                        }
                    } catch (IllegalAccessException e) {
                        userLogger.info("Exception: illegal access: " + e.toString());
                        return null;
                    }
                }
            } else {
                userLogger.info("DF is null: " + entry.getKey());
            }
        }
        return resultList;
    }

}
