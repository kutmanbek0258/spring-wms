package kg.kutman.smanov.sumsarproject.wmsservice.references.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long>, JpaSpecificationExecutor<Depot> {
}