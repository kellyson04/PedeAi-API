package dev.kellyson.projeto.PedeAi.API.address.controller;

import dev.kellyson.projeto.PedeAi.API.address.dto.AddressResponseDTO;
import dev.kellyson.projeto.PedeAi.API.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/me")
    public ResponseEntity<List<AddressResponseDTO>> myAddresses(Authentication authentication) {
        return ResponseEntity.ok(addressService.myAddresses(authentication));
    }
}
