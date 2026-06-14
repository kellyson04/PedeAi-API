package dev.kellyson.projeto.PedeAi.API.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO(
        @NotBlank
        @Pattern(regexp = "^[0-9]{8}$")
        String cep,

        @NotBlank
        @Size(max = 20)
        String number,

        @Size(max = 100)
        String complement
) {
}
