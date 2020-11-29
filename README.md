# phonosemantics.api
phonosemantics v2 functionality in a REST API form

1. Start: mvn clean install, then run generated jar file.
or skip tests: mvn clean -DskipTests install
2. See API (UI): http://localhost:8083/swagger-ui.html
OR get API json: http://localhost:8083/v2/api-docs

Check code coverage via SonarQube (it should be started on 9000 port):
в файле sonar-project.properties прописан url, так что запустить анализ можно просто:
mvn sonar:sonar

однако конфигурацию можно задать и черел командную строку:
mvn sonar:sonar -Dsonar.host.url=http://localhost:9000

больше опиций тут: https://www.devopsschool.com/tutorial/sonarqube/sonarqube-properties.html

TODO:
0. сделать тестовый файл и скармливать его через конструктор, вместо зашитого адреса
1. PhonemesBank устранить путаницу с методами получения списка фонем для таблицы UI
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