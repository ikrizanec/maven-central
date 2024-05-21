package com.infobip.pmf.course.mavencentral;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.Date;

public record Version(
        @NotNull Long id,
        @NotBlank @Pattern(regexp = Version.SEMANTIC_VERSION_REGEX) String semanticVersion,
        String description,
        @NotNull Boolean deprecated,
        @NotNull @Pattern(regexp = Version.DATE_TIME_REGEX) LocalDateTime releaseDate,
        @NotNull Library library
) {
    public static final String SEMANTIC_VERSION_REGEX = "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)$";
    public static final String DATE_TIME_REGEX = "";
}
