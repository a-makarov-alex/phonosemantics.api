package phonosemantics.meaning;

import java.util.Map;

public class Meaning {
    private String definition;
    private Map<SemanticTag, Boolean> tags;

    public Meaning(String definition) {
        this.definition = definition;
        // TODO: bank of tags for definitions
        // записывать теги тут, получать список из отдельного метода, где захардкожены эти теги.
    }

    public String getDefinition() {
        return definition;
    }

    public Map<SemanticTag, Boolean> getTags() {
        return tags;
    }

    public boolean checkTag(String tagName) {
        SemanticTag sTag = SemanticTag.valueOf(tagName.toUpperCase());
        return this.tags.get(sTag);
    }

    public enum SemanticTag {
        BIG, SMALL,
        ROUND, STRAIGHT,        // реально надо прямой/кривой и круглый/неокруглый
        DULL, SHARP
    }
}
