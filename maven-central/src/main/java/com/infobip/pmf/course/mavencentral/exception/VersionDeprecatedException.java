package com.infobip.pmf.course.mavencentral.exception;

public class VersionDeprecatedException extends RuntimeException {
    String message;
    public VersionDeprecatedException(){
    }
    public VersionDeprecatedException(Long libraryId, Long versionId) {
        super();
        this.message = "Version with id '%s' for library with id '%s' is deprecated."
                .formatted(libraryId, versionId);
    }

    public String getMessage(){
        return message;
    }
}
