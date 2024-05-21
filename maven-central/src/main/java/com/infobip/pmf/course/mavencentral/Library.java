package com.infobip.pmf.course.mavencentral;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Library (
        Long id,
        @NotNull String groupId,
        @NotNull String artifactId,
        List<Version> versions,
        @NotNull String name,
        String description
){
}
