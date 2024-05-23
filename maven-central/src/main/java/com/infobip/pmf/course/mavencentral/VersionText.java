package com.infobip.pmf.course.mavencentral;

import jakarta.validation.constraints.NotNull;

public record VersionText (
        @NotNull String description,
        Boolean deprecated
){
}
