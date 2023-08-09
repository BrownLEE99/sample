package backland.sample.domain.phone.entity;

import backland.sample.domain.person.entity.PersonOneToMany;
import backland.sample.global.entity.PrimaryKeyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhoneOneToMany extends PrimaryKeyEntity {
    private String phone;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "fk_person_id",
            referencedColumnName = "id"
    )
    private PersonOneToMany personOneToMany;
}
