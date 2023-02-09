package net.nikiwhite.cryptoservice.personcrypto.repository;

import net.nikiwhite.cryptoservice.personcrypto.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByEmail(String email);
}
