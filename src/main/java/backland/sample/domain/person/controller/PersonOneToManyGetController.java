package backland.sample.domain.person.controller;

import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.service.PersonOneToManyGetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/members")
public class PersonOneToManyGetController {

    private final PersonOneToManyGetService personOneToManyGetService;

    @GetMapping
    public ResponseEntity<String> findAllUsers() {
        List<PersonDTO> personDTOS =
                personOneToManyGetService.findAllUsers();
        return ResponseEntity.ok(personDTOS.toString());
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<String> findByPhone(@PathVariable String phone) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setPhone(phone);
        List<PersonDTO> personDTOS =
                personOneToManyGetService.findByPhone(personDTO);
        if(personDTOS == null ||  personDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 번호는 등록되어 있지 않습니다.");
        }
        else{
            return ResponseEntity.ok(personDTOS.toString());
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<String> findByName(@PathVariable String name) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(name);
        List<PersonDTO> personDTOS =
                personOneToManyGetService.findByName(personDTO);
        if(personDTOS == null || personDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자는 등록되어 있지 않습니다.");
        }
        else{
            return ResponseEntity.ok(personDTOS.toString());
        }
    }
}
