package kg.kutman.smanov.sumsarproject.wmsservice.references.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}