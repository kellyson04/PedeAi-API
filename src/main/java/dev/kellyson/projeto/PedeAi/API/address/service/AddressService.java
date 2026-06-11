package dev.kellyson.projeto.PedeAi.API.address.service;

import dev.kellyson.projeto.PedeAi.API.address.dto.AddressResponseDTO;
import dev.kellyson.projeto.PedeAi.API.address.mapper.AddressMapper;
import dev.kellyson.projeto.PedeAi.API.address.repository.AddressRepository;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional(readOnly = true)
    public List<AddressResponseDTO> myAddresses(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return addressRepository.findByUser(user).stream()
                .map(address -> AddressMapper.toResponse(address))
                .toList();
    }
}
