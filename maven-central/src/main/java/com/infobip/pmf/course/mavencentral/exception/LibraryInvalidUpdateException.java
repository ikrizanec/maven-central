package com.infobip.pmf.course.mavencentral.exception;

public class LibraryInvalidUpdateException extends  RuntimeException{
    String message;
    public LibraryInvalidUpdateException() {}
    public LibraryInvalidUpdateException(Long id){
        super();
        this.message = "Invalid update for library with id '%s'.".formatted(id);
    }

    public String getMessage(){
        return message;
    }
}
