package phonosemantics.output.header;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import phonosemantics.data.PortConfig;

import java.util.List;

@RestController
public class HeaderController {
    @CrossOrigin(origins = PortConfig.FRONTEND_URL)
    @GetMapping("/headers")
    public List<Header> getHeaders() {
        return HeaderService.getNormalityHeaders();
    }
}
