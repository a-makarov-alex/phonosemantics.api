package phonosemantics.output.header;

import java.util.ArrayList;
import java.util.List;

public final class HeaderService {

    public static List<Header> getNormalityHeaders() {
        List<Header> headers = new ArrayList<>();

        //COMMON HEADERS, ROW 1
        Header h = new Header(0,0, "Semantics");
        h.setHeight(3);
        headers.add(h);
        h = new Header(0, 1, "Wordlist length");
        h.setHeight(3);
        headers.add(h);
        h = new Header(0, 2, "later");
        h.setHeight(3);
        headers.add(h);
        headers.add(new Header(0,3,"VOWELS", 12));
        //TODO: headers.add(new Header(0, 2, kindOfStats.toString()));

        //VOWELS HEADERS ROW 2
        headers.add(new Header(1, 3, "Height", 5));
        headers.add(new Header(1, 8, "Backness", 3));
        headers.add(new Header(1, 11, "Roundness", 2));
        headers.add(new Header(1, 13, "Nasalization", 2));


        //VOWELS HEADERS ROW 3
        headers.add(new Header(2, 3, "Open"));
        headers.add(new Header(2, 4, "Op-mid"));
        headers.add(new Header(2, 5, "Mid"));
        headers.add(new Header(2, 6, "Cl-mid"));
        headers.add(new Header(2, 7, "Close"));

        headers.add(new Header(2, 8, "Front"));
        headers.add(new Header(2, 9, "Cent"));
        headers.add(new Header(2, 10, "Back"));

        headers.add(new Header(2,11, "Round"));
        headers.add(new Header(2,12, "Unround"));

        headers.add(new Header(2, 13, "Nasal"));
        headers.add(new Header(2, 14, "Non-Nasal"));

        //TODO: добавить остальные заголовки

        return headers;
    }

    // cause it's a utility class
    private HeaderService() {
    }
}
