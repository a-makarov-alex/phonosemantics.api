package phonosemantics.test;

import org.springframework.web.bind.annotation.*;
import phonosemantics.model.Word;

@RestController
public class ControllerEx {

    @PostMapping("/test")
    public Word pay(@RequestParam(value = "meaning") String meaning, @RequestBody RequestEx request) {
        final ResponseEx response;


        return null;
    }


}
