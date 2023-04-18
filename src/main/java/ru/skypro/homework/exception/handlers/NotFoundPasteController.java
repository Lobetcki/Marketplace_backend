package ru.skypro.homework.exception.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.skypro.homework.exception.InvalidParametersExeption;
import ru.skypro.homework.exception.PasteNotFoundException;

@ControllerAdvice
public class NotFoundPasteController {
    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<?> notFound() {
        return ResponseEntity.status(404).body("Not Found");
    }

    @ExceptionHandler(InvalidParametersExeption.class)
    public ResponseEntity<?> invalidParam() {
        return ResponseEntity.status(400).body("Неверно введены данные");
    }

}
