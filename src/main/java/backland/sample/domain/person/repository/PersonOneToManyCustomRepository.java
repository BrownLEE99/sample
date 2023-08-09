package backland.sample.domain.person.repository;

import backland.sample.domain.person.entity.dto.PersonDTO;

import java.util.List;
import java.util.UUID;

public interface PersonOneToManyCustomRepository {

    // 전체 회원 정보 조회
    List<PersonDTO> findAllUsers();

    // 이름으로 회원 정보 찾기
    List<PersonDTO> findByName(PersonDTO personDTO);
    // 번호 회원 정보 찾기
    List<PersonDTO> findByPhone(PersonDTO personDTO);

    // 이름으로 id 찾기
    UUID getByName(PersonDTO personDTO);
    // 번호로 id 찾기
    UUID getByPhone(PersonDTO personDTO);
}
