package net.nikiwhite.cryptoservice.cryptosingservice.repository;

import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.model.PersonKeyStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonKeyStoreRepository extends JpaRepository<PersonKeyStore, Long> {
    void deleteByPerson(Person person);
}
