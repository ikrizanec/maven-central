package com.infobip.pmf.course.mavencentral.api;

import com.infobip.pmf.course.mavencentral.exception.VersionDefinitionConflictException;
import com.infobip.pmf.course.mavencentral.exception.VersionDeprecatedException;
import com.infobip.pmf.course.mavencentral.exception.VersionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VersionErrorHandler {
    @ExceptionHandler(VersionDefinitionConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public LibraryErrorHandler.ErrorResponse handleVersionDefinitionConflict(VersionDefinitionConflictException ex) {
        return new LibraryErrorHandler.ErrorResponse(ex.getMessage(),
                "Use POST method to create a new version if needed.");
    }

    @ExceptionHandler(VersionDeprecatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public LibraryErrorHandler.ErrorResponse handleVersionIsDeprecated(VersionDeprecatedException ex) {
        return new LibraryErrorHandler.ErrorResponse(ex.getMessage(),
                "Use POST method to create a new version if needed.");
    }
    @ExceptionHandler(VersionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public LibraryErrorHandler.ErrorResponse handleVersionNotFound(VersionNotFoundException ex) {
        return new LibraryErrorHandler.ErrorResponse(ex.getMessage(),
                "Use POST method to create a new version if needed.");
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
