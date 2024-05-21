package com.infobip.pmf.course.mavencentral.exception;

public class LibraryNotFoundException extends RuntimeException {
    String message;
    public LibraryNotFoundException(){
    }
    public LibraryNotFoundException(Long id) {
        super();
        this.message = "Library with id '%s' not found.".formatted(id);
    }

    public String getMessage(){
        return message;
    }
}
