package dev.kellyson.projeto.PedeAi.API.address.repository;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
