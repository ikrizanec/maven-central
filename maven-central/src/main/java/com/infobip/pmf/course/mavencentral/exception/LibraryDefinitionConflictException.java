package com.infobip.pmf.course.mavencentral.exception;

public class LibraryDefinitionConflictException extends RuntimeException{
    String message;

    public LibraryDefinitionConflictException(){
    }

    public LibraryDefinitionConflictException(String groupId, String artifactId){
        super();
        this.message = "Library with given '%s' groupId and '%s' artifactId already exists.".formatted(groupId, artifactId);
    }

    public String getMessage(){
        return message;
    }
}
