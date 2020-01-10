package phonosemantics.output;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class HeaderController {
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/headers")
    public ArrayList<Header> getHeaders() {
        return HeaderService.getNormalityHeaders();
    }
}
