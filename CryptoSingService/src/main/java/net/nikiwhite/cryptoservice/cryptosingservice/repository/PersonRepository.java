package net.nikiwhite.cryptoservice.cryptosingservice.repository;

import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Person findByEmail(String email);
}
