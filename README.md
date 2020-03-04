# phonosemantics.api
phonosemantics v2 functionality in a REST API form

1. Start: mvn clean install, then run generated jar file.
2. See API (UI): http://localhost:8083/swagger-ui.html
OR get API json: http://localhost:8083/v2/api-docs


TODO:

!!!
/wordlists/{meaning}/phonemes не работает. Зацикливается.

!!!
- Phonemes list may be get from PhonemesCoverageNew or PhonemesBank class.
Need to block second opportunity. It's ambiguous.
1. Централизовано хранить фонемы
2. Это должна быть коллекция (сет?) phonemeInTable, HashMap<String, DistFeatures> НЕ подойдет

- count Features instances (just like Phonotypes earlier)
- how to do with classes duplication like Word vs WordReduced
Reduced classes are needed for UI implementation. 
    Language
    1. Implement PhonemesBank instead of SoundsBank in Language (LanguageReduced???) class
    2. Remove SoundBankController class. Move all methods to PhonemesBank class.

- Think over Feature class like:
Feature
private String name;
private Object[] values; //(values from Enums in distinctiveFeatures directory)

- enable CORS using global config (now it's an individual method's config)


____

###/phonemes/parameters/{cluster} 
instead of /phonotypes (SoundsBank class)