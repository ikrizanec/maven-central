package com.infobip.pmf.course.mavencentral.exception;

public class VersionDefinitionConflictException extends RuntimeException{
    String message;
    public VersionDefinitionConflictException(){}

    public VersionDefinitionConflictException(String message){
        super();
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
