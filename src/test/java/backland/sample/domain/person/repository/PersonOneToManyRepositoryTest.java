package backland.sample.domain.person.repository;

import backland.sample.domain.person.entity.PersonOneToMany;
import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.phone.entity.PhoneOneToMany;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class PersonOneToManyRepositoryTest {
    @Autowired
    private PersonOneToManyRepository personOneToManyRepository;

    @BeforeEach
    void init(){
        personOneToManyRepository.deleteAll();
    }

    @Test
    void save() {
        // given
        // 이름 john 번호 : 010-5278-3309
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName("john");
        List<PhoneOneToMany> phoneOneToManyList = new ArrayList<>();
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone("010-5278-3309");
        phoneOneToMany.setPersonOneToMany(personOneToMany);
        phoneOneToManyList.add(phoneOneToMany);
        personOneToMany.setPhoneOneToMany(phoneOneToManyList);
        personOneToManyRepository.save(personOneToMany);

        // when
        // 단순 UUID로 찾는다
        PersonOneToMany find = personOneToManyRepository.findById(personOneToMany.getId()).get();

        // then
        // UUID로 찾은 것과 저장한 것이 동일한 지 확인
        Assertions.assertThat(find).isEqualTo(personOneToMany);
    }

    @Test
    void findByName(){
        // given
        // 이름 john 번호 : 010-5278-3309
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName("john");
        List<PhoneOneToMany> phoneOneToManyList = new ArrayList<>();
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone("010-5278-3309");
        phoneOneToMany.setPersonOneToMany(personOneToMany);
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany();
        phoneOneToMany1.setPhone("010-1234-1233");
        phoneOneToMany1.setPersonOneToMany(personOneToMany);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany();
        phoneOneToMany2.setPhone("010-3333-1111");
        phoneOneToMany2.setPersonOneToMany(personOneToMany);


        phoneOneToManyList.add(phoneOneToMany);
        phoneOneToManyList.add(phoneOneToMany1);
        phoneOneToManyList.add(phoneOneToMany2);
        personOneToMany.setPhoneOneToMany(phoneOneToManyList);
        personOneToManyRepository.save(personOneToMany);
        //when
        PersonDTO input = new PersonDTO();
        input.setName("john");

        List<PersonDTO> personManyDTO = personOneToManyRepository.findByName(input);
        for (PersonDTO personDTO : personManyDTO) {
            log.info("john's phone = {}" , personDTO.getPhone());
        }
        Assertions.assertThat(personManyDTO.size()).isEqualTo(3);
    }

    @Test
    void findByPhone(){
        // given
        // 이름 john 번호 : 010-5278-3309
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName("john");
        List<PhoneOneToMany> phoneOneToManyList = new ArrayList<>();
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone("010-5278-3309");
        phoneOneToMany.setPersonOneToMany(personOneToMany);
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany();
        phoneOneToMany1.setPhone("010-1234-1233");
        phoneOneToMany1.setPersonOneToMany(personOneToMany);
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany();
        phoneOneToMany2.setPhone("010-3333-1111");
        phoneOneToMany2.setPersonOneToMany(personOneToMany);


        phoneOneToManyList.add(phoneOneToMany);
        phoneOneToManyList.add(phoneOneToMany1);
        phoneOneToManyList.add(phoneOneToMany2);
        personOneToMany.setPhoneOneToMany(phoneOneToManyList);
        personOneToManyRepository.save(personOneToMany);

        //when
        PersonDTO input = new PersonDTO();
        input.setPhone("010-5278-3309");
        List<PersonDTO> personManyDTO = personOneToManyRepository.findByPhone(input);
        for (PersonDTO personDTO : personManyDTO) {
            log.info("john's phone = {}" , personDTO.getPhone());
        }
        Assertions.assertThat(personManyDTO.size()).isEqualTo(3);

    }

    @Test
    void findAllUsers(){
        // 이름 john 번호 : 010-5278-3309
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName("john");
        List<PhoneOneToMany> phoneOneToManyList = new ArrayList<>();
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        phoneOneToMany.setPhone("010-5278-3309");
        phoneOneToMany.setPersonOneToMany(personOneToMany);
        PhoneOneToMany phoneOneToMany1 = new PhoneOneToMany();
        phoneOneToMany1.setPhone("010-1234-1233");
        phoneOneToMany1.setPersonOneToMany(personOneToMany);

        phoneOneToManyList.add(phoneOneToMany);
        phoneOneToManyList.add(phoneOneToMany1);
        personOneToMany.setPhoneOneToMany(phoneOneToManyList);
        personOneToManyRepository.save(personOneToMany);

        // 이름 dan
        PersonOneToMany personOneToMany1 = new PersonOneToMany();
        personOneToMany1.setName("dan");
        List<PhoneOneToMany> phoneOneToManyList1 = new ArrayList<>();
        PhoneOneToMany phoneOneToMany2 = new PhoneOneToMany();
        phoneOneToMany2.setPhone("010-5278-3309");
        phoneOneToMany2.setPersonOneToMany(personOneToMany1);
        PhoneOneToMany phoneOneToMany3 = new PhoneOneToMany();
        phoneOneToMany3.setPhone("010-1234-1233");
        phoneOneToMany3.setPersonOneToMany(personOneToMany1);

        phoneOneToManyList1.add(phoneOneToMany2);
        phoneOneToManyList1.add(phoneOneToMany3);
        personOneToMany1.setPhoneOneToMany(phoneOneToManyList1);
        personOneToManyRepository.save(personOneToMany1);

        List<PersonDTO> findAll = personOneToManyRepository.findAllUsers();

        log.info("All user Info = {}", findAll);
        Assertions.assertThat(findAll.size()).isEqualTo(4);
    }
}