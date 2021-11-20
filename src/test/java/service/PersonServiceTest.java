package service;

import com.postcodelotterie.assessment.model.Person;
import com.postcodelotterie.assessment.repository.PersonRepository;
import com.postcodelotterie.assessment.service.PersonService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/*@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc*/
@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;


    @Test
    public void shouldCreatePersonSuccessfully(){
        Person person = new Person(1L, "Raghunandan", "Palakodety", "07.11.1987");
        doReturn(person).when(personRepository).save(person);
        Person person1 = personService.save(person);
        assertThat(person1).isEqualTo(person);
    }

    @Test
    public void shouldReturnFindAll(){
        List<Person> persons = List.of(new Person(1L, "Raghu", "Palakodety", "07.11.1987"),
                new Person(2L, "Damian", "Deßler", "08.11.1987"));
        given(personRepository.findAll()).willReturn(persons);
        List<Person> expected = personService.findAll();
        assertEquals(expected, persons);
    }

    @Test
    public void findPersonById(){
        final Long id = 1L;
        final Person person = new Person(1L, "Raghu", "Palakodety", "07.11.1987");
        given(personRepository.findById(id)).willReturn(Optional.of(person));
        final Optional<Person> expectedPerson = personService.findById(id);
        assertThat(expectedPerson).isNotNull();
    }

    @Test
    public void shouldBeDelete(){
        final Long id = 1L;
        personService.deleteById(id);
        personService.deleteById(id);
        verify(personRepository, times(2)).deleteById(id);
    }
}
