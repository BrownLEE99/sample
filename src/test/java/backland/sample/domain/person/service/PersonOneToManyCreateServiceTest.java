package backland.sample.domain.person.service;

import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.repository.PersonOneToManyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PersonOneToManyCreateServiceTest {
    @Autowired
    private  PersonOneToManyCreateService personOneToManyCreateService;
    @Autowired
    private  PersonOneToManyRepository personOneToManyRepository;

    @BeforeEach
    void init(){
        personOneToManyRepository.deleteAll();

    }

    @Test
    void save(){
        // given
        PersonDTO personDto = new PersonDTO();
        personDto.setName("john");
        personDto.setPhone("010-1234-5678");
        personOneToManyCreateService.save(personDto);

        // when
        List<PersonDTO> findDTO = personOneToManyRepository.findByName(personDto);

        // then
        Assertions.assertThat(findDTO.get(0)).isEqualTo(personDto);
    }

    @Test
    void changeUserName(){
        // given
        PersonDTO personDto = new PersonDTO();
        personDto.setName("john");
        personDto.setPhone("010-1234-5678");
        personOneToManyCreateService.save(personDto);

        // when
        personDto.setName("Monster");
        personOneToManyCreateService.save(personDto);
        List<PersonDTO> findDTO = personOneToManyRepository.findByName(personDto);

        // then
        Assertions.assertThat(findDTO.get(0)).isEqualTo(personDto);
        // Please check the log
        // it should be
        // User Already Exist, Change User name to = {username}
    }

    @Test
    void addUserPhone(){
        // given
        PersonDTO personDto = new PersonDTO();
        personDto.setName("john");
        personDto.setPhone("010-1234-5678");
        personOneToManyCreateService.save(personDto);

        // when
        personDto.setPhone("010-4321-1111");
        personOneToManyCreateService.save(personDto);
        List<PersonDTO> findDTO = personOneToManyRepository.findByName(personDto);

        // then
        Assertions.assertThat(findDTO.get(1)).isEqualTo(personDto);
        // Please check the log
        // it should be
        // User Already Exist, Change User name to = {username}
    }
}