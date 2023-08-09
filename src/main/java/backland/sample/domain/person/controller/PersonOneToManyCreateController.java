package backland.sample.domain.person.controller;

import backland.sample.domain.person.entity.dto.PersonDTO;
import backland.sample.domain.person.service.PersonOneToManyCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PersonOneToManyCreateController {

    private final PersonOneToManyCreateService personOneToManyCreateService;

    @PostMapping("/v1/members")
    public ResponseEntity<String> create(@RequestBody @Valid PersonDTO personDTO) {
        boolean saveResult = personOneToManyCreateService.save(personDTO);
        if (saveResult == true) {
            return ResponseEntity.ok(personDTO.toString());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "Critical Error!! : Server에 저장을 실패하였습니다."
            );
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}
