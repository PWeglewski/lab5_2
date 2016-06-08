package edu.iis.mto.integrationtest.repository;

import edu.iis.mto.integrationtest.model.Person;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static edu.iis.mto.integrationtest.repository.PersonBuilder.person;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class PersonRepositoryIntegrationTest extends IntegrationTest {

    private static final long UPDATE_TEST_PERSON_ID = 3000L;
    private static final String UPDATE_TEST_PERSON_NEW_FIRST_NAME = "Janusz";
    private static final String UPDATE_TEST_PERSON_NEW_LAST_NAME = "Testowy";

    private static final long DELETE_TEST_PERSON_ID = 4000L;

    private static final int FIND_BY_FIRST_NAME_TEST_LIST_SIZE = 5;
    private static final String FIND_BY_FIRST_NAME_TEST_NAME = "Mieczyslaw";

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCanAccessDbAndGetTestData() {
        List<Person> foundTestPersons = personRepository.findAll();
        // We should not rely on test order
        assertThat(foundTestPersons.size()).isNotEqualTo(0);
    }

    @Test
    public void testSaveNewPersonAndCheckIsPersisted() {
        long count = personRepository.count();
        personRepository.save(a(person().withId(count + 1)
                .withFirstName("Roberto").withLastName("Mancini")));
        assertEquals(count + 1, personRepository.count());
        assertEquals("Mancini", personRepository.findOne(count + 1)
                .getLastName());
    }

    @Test
    public void testUpdatePerson() {
        // given
        Person person = personRepository.findOne(UPDATE_TEST_PERSON_ID);

        //when
        person.setFirstName(UPDATE_TEST_PERSON_NEW_FIRST_NAME);
        person.setLastName(UPDATE_TEST_PERSON_NEW_LAST_NAME);
        personRepository.save(person);
        Person updatedPerson = personRepository.findOne(UPDATE_TEST_PERSON_ID);

        //then
        assertThat(updatedPerson.getId()).isEqualTo(UPDATE_TEST_PERSON_ID);
        assertThat(updatedPerson.getFirstName()).isEqualTo(UPDATE_TEST_PERSON_NEW_FIRST_NAME);
        assertThat(updatedPerson.getLastName()).isEqualTo(UPDATE_TEST_PERSON_NEW_LAST_NAME);

    }

    @Test
    public void testDeletePerson() {
        // given
        Person person = personRepository.findOne(DELETE_TEST_PERSON_ID);
        assertThat(person).isNotNull();

        // when
        personRepository.delete(person);

        // then
        person = personRepository.findOne(DELETE_TEST_PERSON_ID);
        assertThat(person).isNull();
    }

    @Test
    public void testFindByFirstNameLike() {
        // given
        List<Person> personList;

        // when
        personList = personRepository.findByFirstNameLike(FIND_BY_FIRST_NAME_TEST_NAME);

        // then
        assertThat(personList.size()).isEqualTo(FIND_BY_FIRST_NAME_TEST_LIST_SIZE);
    }

    private Person a(PersonBuilder builder) {
        return builder.build();
    }
}
