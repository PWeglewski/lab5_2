package edu.iis.mto.integrationtest.main;

import edu.iis.mto.integrationtest.utils.Mode;
import edu.iis.mto.integrationtest.utils.ModeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApplicationMain {

//    private static ApplicationContext applicationContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        /* Ustawienie trybu dzia�ania aplikacji */
        ModeUtils.setMode(Mode.DEV);
        LOGGER.debug("Application mode set to ", Mode.DEV);

//        /* Konfiguracja kontekstu Spring i pobranie z kontenera bean'a reprezentujacego repozytorium danych*/
//        applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        PersonRepository repository = applicationContext.getBean(PersonRepository.class);
//
//        /* Wykonanie operacji na danych */
//        /* Wyszukanie rekordu */
//        Person foundDevPerson = repository.findOne(1L);
//        LOGGER.debug("Found person is: {}", foundDevPerson);
//
//        /* Pobranie liczby rekord�w w repozytorium */
//        long recordsNumber = repository.count();
//        LOGGER.debug("Found {} records in total", recordsNumber);
    }
}
