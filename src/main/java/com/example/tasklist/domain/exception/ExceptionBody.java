package com.example.tasklist.domain.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionBody {

    String message;
    Map<String, List<String>> errors = new HashMap<>();

    public ExceptionBody(final String message) {
        this.message = message;
    }

    public ExceptionBody(final String message,
                         final Map<String, List<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
