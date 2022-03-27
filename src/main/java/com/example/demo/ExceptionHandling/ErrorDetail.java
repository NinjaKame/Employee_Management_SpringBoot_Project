package com.example.demo.ExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetail {
    private Date timeStamp;
    private String message;
    private HttpStatus status;
    private String description;

}
