package com.infobip.pmf.course.mavencentral.exception;

public class VersionNotFoundException extends RuntimeException {
    String message;
    public VersionNotFoundException(){
    }
    public VersionNotFoundException(Long libraryId, Long versionId) {
        super();
        this.message = "Library with id '%s' and versionId '%d' not found.".formatted(libraryId, versionId);
    }

    public String getMessage(){
        return message;
    }

}
