package com.leaf.helabojun.user.config;


import com.leaf.helabojun.user.dto.common.ErrorResponseDTO;
import com.leaf.helabojun.user.enums.ResponseEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.utility.MessageResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageResource messageResource;

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<Object> handleDataNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponseDTO<>(ResponseEnum.ERROR, ex.getMessage(), LocalDateTime.now(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    public final ResponseEntity<Object> handleDataConflictException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponseDTO<>(ResponseEnum.ERROR, ex.getMessage(), LocalDateTime.now(), request.getDescription(false)), HttpStatus.CONFLICT);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> messageResource.getMessage(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponseDTO<>(ResponseEnum.ERROR, errors, LocalDateTime.now(), request.getDescription(false)), status);
    }
}
