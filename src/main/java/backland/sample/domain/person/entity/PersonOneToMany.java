package backland.sample.domain.person.entity;

import backland.sample.domain.phone.entity.PhoneOneToMany;
import backland.sample.global.entity.PrimaryKeyEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class PersonOneToMany extends PrimaryKeyEntity {
    private String name;

    @OneToMany(
            mappedBy = "personOneToMany",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<PhoneOneToMany> phoneOneToMany;
}
