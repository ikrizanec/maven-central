package com.infobip.pmf.course.mavencentral;

import jakarta.validation.constraints.NotNull;

public record LibraryText (
        @NotNull String name,
        String description
){
}
