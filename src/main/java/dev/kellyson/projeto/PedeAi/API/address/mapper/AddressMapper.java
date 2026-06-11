package dev.kellyson.projeto.PedeAi.API.address.mapper;

import dev.kellyson.projeto.PedeAi.API.address.dto.AddressResponseDTO;
import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressMapper {

    public static AddressResponseDTO toResponse(Address address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .complement(address.getComplement())
                .createdAt(address.getCreatedAt())
                .build();
    }
}
