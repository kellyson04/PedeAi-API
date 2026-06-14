package dev.kellyson.projeto.PedeAi.API.address.service;

import dev.kellyson.projeto.PedeAi.API.address.dto.AddressRequestDTO;
import dev.kellyson.projeto.PedeAi.API.address.dto.AddressResponseDTO;
import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.address.mapper.AddressMapper;
import dev.kellyson.projeto.PedeAi.API.address.repository.AddressRepository;
import dev.kellyson.projeto.PedeAi.API.config.viacep.dto.ViaCepResponseDTO;
import dev.kellyson.projeto.PedeAi.API.config.viacep.service.ViaCepService;
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
    private final ViaCepService viaCepService;

    @Transactional(readOnly = true)
    public List<AddressResponseDTO> myAddresses(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return addressRepository.findByUser(user).stream()
                .map(address -> AddressMapper.toResponse(address))
                .toList();
    }

    @Transactional
    public AddressResponseDTO registerAddress(AddressRequestDTO request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        ViaCepResponseDTO addressData = viaCepService.findAdressByCep(request.cep());

        Address address = Address.builder()
                .user(user)
                .street(addressData.logradouro())
                .number(request.number())
                .neighborhood(addressData.bairro())
                .city(addressData.localidade())
                .state(addressData.uf())
                .zipCode(addressData.cep())
                .complement(request.complement())
                .build();

        addressRepository.save(address);

        return AddressMapper.toResponse(address);
    }
}
