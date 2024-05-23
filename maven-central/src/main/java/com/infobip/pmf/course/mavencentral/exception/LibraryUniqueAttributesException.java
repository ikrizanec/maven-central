package com.infobip.pmf.course.mavencentral.exception;
public class LibraryUniqueAttributesException extends  RuntimeException{
    String message;
    public LibraryUniqueAttributesException() {}
    public LibraryUniqueAttributesException(String groupId, String artifactId){
        super();
        this.message = "Library with given '%s'  groupId and '%s' artifactId already exists.".formatted(groupId, artifactId);
    }
    public String getMessage(){
        return message;
    }
}
