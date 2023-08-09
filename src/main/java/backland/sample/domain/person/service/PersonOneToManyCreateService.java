package backland.sample.domain.person.service;

import backland.sample.domain.person.entity.PersonOneToMany;
import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.repository.PersonOneToManyRepository;
import backland.sample.domain.phone.entity.PhoneOneToMany;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonOneToManyCreateService {
    private final PersonOneToManyRepository personOneToManyRepository;
    private final PersonOneToManyUpdateService personOneToManyUpdateService;

    /**
     * (이름,번호)를 받아 회원을 저장하는 메소드
     * 이름이 이미 사용 중인 경우 : 전화번호를 추가한다
     * 번호가 이미 존재하는 경우 : 이름을 변경한다.
     * 둘 다 없는 경우 : 멤버를 새로 추가한다.
     * @param personDTO (이름, 번호)
     * @return 잘 저장이 되었으면 true, 오류가 발생하면 false 반환
     */
    @Transactional
    public boolean save(PersonDTO personDTO){
        String name = personDTO.getName();
        String phone = personDTO.getPhone();

        // 해당 번호가 저장이 되어 있는가?
        UUID getByPhone = personOneToManyRepository.getByPhone(personDTO);
        if(getByPhone != null){
            // 해당 사용자의 이름만 변경한다
            PersonOneToMany personOneToMany = personOneToManyRepository.findById(getByPhone).get();
            personOneToMany.setName(name);
            personOneToManyRepository.save(personOneToMany);
            log.info("User Already Exist, Change User name to ={}",name);
            return true;
        }

        // 해당 사용자가 존재하는가?
        UUID getByName = personOneToManyRepository.getByName(personDTO);
        if(getByName != null){
            // 해당 사용자에 번호만 추가한다.
            PersonOneToMany personOneToMany = personOneToManyRepository.findById(getByName).get();
            PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
            phoneOneToMany.setPhone(phone);
            phoneOneToMany.setPersonOneToMany(personOneToMany);
            personOneToMany.getPhoneOneToMany().add(phoneOneToMany);
            personOneToManyRepository.save(personOneToMany);
            log.info("User Already Exist, Just Add Phone Number : {}",phone);
            return true;
        }

        // 처음 만들어진 회원
        PhoneOneToMany phoneOneToMany = new PhoneOneToMany();
        PersonOneToMany personOneToMany = new PersonOneToMany();
        personOneToMany.setName(name);
        phoneOneToMany.setPhone(phone);
        List<PhoneOneToMany> phoneOneToManyList = new ArrayList<>();
        phoneOneToManyList.add(phoneOneToMany);

        personOneToMany.setPhoneOneToMany(phoneOneToManyList);
        phoneOneToMany.setPersonOneToMany(personOneToMany);

        PersonOneToMany created =  personOneToManyRepository.save(personOneToMany);
        if(created != null){
            log.info("New User Craeted : {}",personDTO);
            return true;
        }
        else{

            return false;
        }
    }
}
