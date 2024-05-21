package com.infobip.pmf.course.mavencentral.exception;

public class LibraryGroupNotFoundException extends RuntimeException {
    String message;
    public LibraryGroupNotFoundException(){
    }
    public LibraryGroupNotFoundException(String groupId, String artifactId){
        super();
        this.message = "Library with '%s' groupId and artifactId '%s' not found .".formatted(groupId, artifactId);
    }

    public String getMessage(){
        return message;
    }
}
