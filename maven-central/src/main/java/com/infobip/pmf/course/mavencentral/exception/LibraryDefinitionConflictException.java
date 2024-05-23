package com.infobip.pmf.course.mavencentral.exception;

public class LibraryDefinitionConflictException extends RuntimeException{
    String message;

    public LibraryDefinitionConflictException(){
    }

    public LibraryDefinitionConflictException(String libraryId){
        super();
        this.message = "Library with given '%s' id  already exists.".formatted(libraryId);
    }

    public String getMessage(){
        return message;
    }
}
