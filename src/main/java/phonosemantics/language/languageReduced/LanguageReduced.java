package phonosemantics.language.languageReduced;

import phonosemantics.language.Language;

public class LanguageReduced {
    private String title;
    private String family;
    private String group;  // typology etc.

    /*public LanguageReduced(Language language) {
        this.title = language.getTitle();
    }*/

    public String getTitle() {
        return title;
    }

    public String getFamily() {
        return family;
    }

    public String getGroup() {
        return group;
    }
}
