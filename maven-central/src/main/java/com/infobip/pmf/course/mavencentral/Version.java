package com.infobip.pmf.course.mavencentral;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record Version(
        Long id,
        @NotNull @Pattern(regexp = Version.SEMANTIC_VERSION_REGEX) String semanticVersion,
        String description,
        @NotNull Boolean deprecated,
        LocalDateTime releaseDate
) {
    public Version {
        if(deprecated == null) {
            deprecated = false;
        }
    }
    public static final String SEMANTIC_VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";

}
