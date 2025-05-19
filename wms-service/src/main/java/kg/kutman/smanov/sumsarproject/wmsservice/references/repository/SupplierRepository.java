package kg.kutman.smanov.sumsarproject.wmsservice.references.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
}