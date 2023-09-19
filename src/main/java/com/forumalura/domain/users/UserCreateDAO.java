package com.forumalura.domain.users;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDAO(@NotBlank String name,@NotBlank String email,@NotBlank String password) {
}
