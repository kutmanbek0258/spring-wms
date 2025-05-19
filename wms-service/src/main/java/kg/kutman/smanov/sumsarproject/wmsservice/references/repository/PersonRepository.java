package kg.kutman.smanov.sumsarproject.wmsservice.references.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
}