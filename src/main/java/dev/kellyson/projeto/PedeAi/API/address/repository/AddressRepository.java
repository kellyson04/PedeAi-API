package dev.kellyson.projeto.PedeAi.API.address.repository;

import dev.kellyson.projeto.PedeAi.API.address.entity.Address;
import dev.kellyson.projeto.PedeAi.API.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);
}
