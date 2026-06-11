package dev.kellyson.projeto.PedeAi.API.config.viacep.service;

import dev.kellyson.projeto.PedeAi.API.config.viacep.dto.ViaCepResponseDTO;
import dev.kellyson.projeto.PedeAi.API.exception.ZipCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final RestClient viaCepRestClient;

    public ViaCepResponseDTO findAdressByCep(String cep) {
        ViaCepResponseDTO response = viaCepRestClient
                .get()
                .uri("/ws/{cep}/json/", cep)
                .retrieve()
                .body(ViaCepResponseDTO.class);

        if (response == null || Boolean.TRUE.equals(response.erro())) {
            throw new ZipCodeNotFoundException("Cep nao encontrado");
        }

        return response;
    }

}
