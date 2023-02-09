package net.nikiwhite.cryptoservice.cryptosingservice.repository;

import net.nikiwhite.cryptoservice.cryptosingservice.model.Person;
import net.nikiwhite.cryptoservice.cryptosingservice.model.Sign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {

    Sign findByNameAndPerson(String name, Person person);

    List<Sign> findByPerson(Person person);
}
