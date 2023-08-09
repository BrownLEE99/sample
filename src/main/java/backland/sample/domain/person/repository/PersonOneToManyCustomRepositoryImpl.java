package backland.sample.domain.person.repository;

import backland.sample.domain.person.entity.QPersonOneToMany;
import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.phone.entity.QPhoneOneToMany;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * QueryDsl를 사용하는 커스텀 레포지터리의 구현체
 */
@Slf4j
@Repository
public class PersonOneToManyCustomRepositoryImpl implements PersonOneToManyCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QPersonOneToMany qPersonOneToMany;
    private final QPhoneOneToMany qPhoneOneToMany;
    public PersonOneToManyCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.qPersonOneToMany = QPersonOneToMany.personOneToMany;
        this.qPhoneOneToMany = QPhoneOneToMany.phoneOneToMany;
    }

    /**
     * 모든 회원의 정보를 반환하는 메소드
     * 이름 순으로 정렬되어 있으며 OneToMany의 innerJoin에서의 중복을 막기 위해
     * distinct 사용
     * @return 모든 회원 (이름, 번호)
     */
    @Override
    public List<PersonDTO> findAllUsers(){
        return jpaQueryFactory
                .select(Projections.constructor(
                                PersonDTO.class,
                                qPersonOneToMany.name,
                                qPhoneOneToMany.phone
                        )
                )
                .from(qPersonOneToMany)
                .innerJoin(qPersonOneToMany.phoneOneToMany, qPhoneOneToMany)
                .orderBy(qPersonOneToMany.name.asc())
                .fetch()
                .stream().distinct().collect(Collectors.toList());
    }

    /**
     * 이름으로 해당 회원 정보 찾기
     * @param personManyDTO (이름,번호)
     * @return 이름(사용자)의 모든 (이름,번호) 쌍을 반환
     */
    @Override
    public List<PersonDTO> findByName(PersonDTO personManyDTO) {
        String name = personManyDTO.getName();
        return jpaQueryFactory
                .select(Projections.constructor(
                                PersonDTO.class,
                                qPersonOneToMany.name,
                                qPhoneOneToMany.phone
                        )
                )
                .from(qPersonOneToMany)
                .innerJoin(qPersonOneToMany.phoneOneToMany, qPhoneOneToMany)
                .where(qPersonOneToMany.name.eq(name))
                .fetch()
                .stream().distinct().collect(Collectors.toList());
    }

    /**
     * 번호로 해당 사용자의 정보 찾기
     * 1. 번호로 이름 ID를 찾는다
     * 2. 해당 ID를 통해 모든 쌍을 반환
     * 해당 번호를 사용하는 사용자가 없으면 빈 컬렉션을 반환
     * @param personDTO (이름,번호)
     * @return 해당 번호를 사용하는 회원의 모든 (이름,번호) 쌍 반환
     */
    @Override
    public List<PersonDTO> findByPhone(PersonDTO personDTO) {
        String phone = personDTO.getPhone();
        UUID userId =
                jpaQueryFactory
                        .select(qPersonOneToMany.id)
                        .from(qPersonOneToMany)
                        .innerJoin(qPersonOneToMany.phoneOneToMany,qPhoneOneToMany)
                        .where(qPhoneOneToMany.phone.eq(phone))
                        .fetchOne();


        if(userId == null) return Collections.emptyList();

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                PersonDTO.class,
                                qPersonOneToMany.name,
                                qPhoneOneToMany.phone
                        )
                )
                .from(qPersonOneToMany)
                .innerJoin(qPersonOneToMany.phoneOneToMany,qPhoneOneToMany)
                .where(
                      qPersonOneToMany.id.eq(userId)
                )
                .fetch()
                .stream().distinct().collect(Collectors.toList());
    }

    /**
     * 이름으로 해당 사용자의 UUID를 반환
     * - 해당 이름이 이미 존재하는지 확인하기 위함
     * @param personDTO (이름,번호)
     * @return 해당 이름(사용자)의 UUID
     */
    @Override
    public UUID getByName(PersonDTO personDTO) {
        return
                jpaQueryFactory
                    .selectDistinct(qPersonOneToMany.id)
                    .from(qPersonOneToMany)
                    .innerJoin(qPersonOneToMany.phoneOneToMany, qPhoneOneToMany)
                    .where(qPersonOneToMany.name.eq(personDTO.getName()))
                    .fetchOne();
    }

    /**
     * 번호로 해당 사용자의 UUID를 반환
     * - 해당 번호가 이미 존재하는 지 확인
     * @param personDTO (이름,번호)
     * @return 해당 번호의 사용자의 UUID
     */
    @Override
    public UUID getByPhone(PersonDTO personDTO) {
        return
                jpaQueryFactory
                        .selectDistinct(qPersonOneToMany.id)
                        .from(qPersonOneToMany)
                        .innerJoin(qPersonOneToMany.phoneOneToMany,qPhoneOneToMany)
                        .where(qPhoneOneToMany.phone.eq(personDTO.getPhone()))
                        .fetchOne();
    }
}
