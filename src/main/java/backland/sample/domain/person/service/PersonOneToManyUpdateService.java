package backland.sample.domain.person.service;

import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.repository.PersonOneToManyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonOneToManyUpdateService {
    private final PersonOneToManyRepository personOneToManyRepository;

    // 기존 코드에서는 해당 서비스를 사용해서 회원정보를 수정
    // 수정한 코드는 JPAReposiotry의 save 메소드를 사용해서 정보를 수정
}
