package backland.sample.domain.person.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PersonDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Name cannot contain special characters")
    private String name;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "Invalid phone number format. Use XXX-XXX-XXXX format.")
    private String phone;

    public PersonDTO() {
    }

    @QueryProjection
    public PersonDTO(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
