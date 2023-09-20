package com.forumalura.domain.topics;

import jakarta.validation.constraints.NotBlank;

public record TopicCreateDTO(@NotBlank String title,
                             @NotBlank String message,
                             @NotBlank Long courseId) {
}
