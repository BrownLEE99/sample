package backland.sample.domain.person.repository;

import backland.sample.domain.person.entity.PersonOneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * PersonOneToManyRepository는 JPARepository와 PersonOneToMAnyCustomRepository를 상속 받는다.
 * PersonOneToMAnyCustomRepository는 PersonOneToMAnyCustomRepositoryImpl로 구현이 되어 있으며
 * PersonOneToMAnyCustomRepositoryImpl에 정의된 메소드를 여기 PersonOneToManyRepository에서 사용이 가능하다.
 *
 * 사용자 정의 구현 클래스인 경우 JPA가 파일명이 repository 이름 + Impl 인것을 찾아서
 * 인터페이스에 의존성 주입할때 Impl 객체를 삽입해준다.
 */
public interface PersonOneToManyRepository extends JpaRepository<PersonOneToMany, UUID>, PersonOneToManyCustomRepository {

}
