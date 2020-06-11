package phonosemantics.output.header;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.Port;

import java.util.ArrayList;

@RestController
public class HeaderController {
    @CrossOrigin(origins = Port.FRONTEND_URL)
    @GetMapping("/headers")
    public ArrayList<Header> getHeaders() {
        return HeaderService.getNormalityHeaders();
    }
}
