package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.exception.LibraryDefinitionConflictException;
import com.infobip.pmf.course.mavencentral.exception.LibraryNotFoundException;
import com.infobip.pmf.course.mavencentral.exception.LibraryUniqueAttributesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LibraryErrorHandler {

    @ExceptionHandler(LibraryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleLibraryNotFound(LibraryNotFoundException ex) {

        return new ErrorResponse(ex.getMessage(),
                "Use POST method to create a new library if needed.");
    }

    @ExceptionHandler(LibraryDefinitionConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleLibraryAlreadyExistsId(LibraryDefinitionConflictException ex) {
        return new ErrorResponse(ex.getMessage(),
                "Use POST method to create a new library if needed.");
    }

    @ExceptionHandler(LibraryUniqueAttributesException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleLibraryAlreadyExistsUniqueAritifactGroup(LibraryUniqueAttributesException ex) {

        return new ErrorResponse(ex.getMessage(),
                "Use POST method to create a new library if needed.");
    }

    public record ErrorResponse(String message, String action)
    {
        public ErrorResponse(String message, String action) {
            this.message = message;
            this.action = action;
        }
        public String getMessage() {
            return message;
        }

        public String getAction() {
            return action;
        }
    }

}