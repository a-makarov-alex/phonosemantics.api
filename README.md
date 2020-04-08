# phonosemantics.api
phonosemantics v2 functionality in a REST API form

1. Start: mvn clean install, then run generated jar file.
2. See API (UI): http://localhost:8083/swagger-ui.html
OR get API json: http://localhost:8083/v2/api-docs


TODO:

1. Округлять проценты до 0.111, т.е. 11,1%
2. Выяснить куда деваются фонемы: в вордлисте leaf 144 фонемы, из которых рассчитываются 139. Возможно, остальные unrecognized



3. повторный getLanguage заново пересчитывает calculatePhType




- count Features instances (just like Phonotypes earlier)
- how to do with classes duplication like Word vs WordReduced
Reduced classes are needed for UI implementation. 



- Think over Feature class like:
Feature
private String name;
private Object[] values; //(values from Enums in distinctiveFeatures directory)

- enable CORS using global config (now it's an individual method's config)


____

###/phonemes/parameters/{cluster} 
instead of /phonotypes (SoundsBank class)