# phonosemantics.api
phonosemantics v2 functionality in a REST API form

1. Start: mvn clean install, then run generated jar file.
2. See API (UI): http://localhost:8083/swagger-ui.html
OR get API json: http://localhost:8083/v2/api-docs


TODO:
1. сделать логирование в PhonemesBankController
2. Выяснить куда деваются фонемы: в вордлисте leaf 144 фонемы, из которых рассчитываются 139. Возможно, остальные unrecognized
3. Добавить ендпойнты в ArticulationPattern
4. Протестировать совпадает ли word.length и word.transcription.size();
5. протестировать и удалить тестовый фрагмент в getArticulationPattern (класс Word)
7 ???. Убрать ArticulationPattern внутрь класса Word (public class), но полем класса Word не делать
8. Проверить unknown phonemes и внести их в ТАБЛИЦУ PhonemesCoverageExample, и вообще понять как этот файл используется

большие задачи:
- !!! проработать идею поиска по паттерну. плюс сложный вариант: комбинация параметров. "Звонкий фрикатив + гласная верхнего подъема..."
- сделать нормальные логи
0. добавить аффрикаты
1. добавить диакритику
2. добавить долготу гласных
3. добавить кликсы

3. ИССЛЕДОВАНИЕ: протестировать названия музыкальных инструментов


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