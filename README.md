# phonosemantics.api
phonosemantics v2 functionality in a REST API form

1. Start: mvn clean install, then run generated jar file.
2. See API (UI): http://localhost:8083/swagger-ui.html
OR get API json: http://localhost:8083/v2/api-docs


TODO:

1. PhonemesBank ---> getAllPhonemesList комменты удалить
2. getFeaturesStats не дергает создание вордлистов
3. getLanguages не дергает создание вордлистов и соответственно создание языков

!!!
количество фонем и слов с фонемой всегда совпадает


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