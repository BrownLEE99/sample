package backland.sample.domain.person.service;

import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.repository.PersonOneToManyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonOneToManyGetService {
    private final PersonOneToManyRepository personOneToManyRepository;

    // 모든 회원을 불러오는 메소드
    public List<PersonDTO> findAllUsers(){
        return personOneToManyRepository.findAllUsers();
    }

    // 해당 전화번호를 사용하고 있는 회원의 모든 정보를 불러오는 메소드
    public List<PersonDTO> findByPhone(PersonDTO personDTO){
        return personOneToManyRepository.findByPhone(personDTO);
    }

    // 해당 이름의 사용자의 모든 정보를 불러오는 메소드
    public List<PersonDTO> findByName(PersonDTO personDTO) {
        return personOneToManyRepository.findByName(personDTO);
    }
}
