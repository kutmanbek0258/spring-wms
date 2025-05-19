package kg.kutman.smanov.sumsarproject.wmsservice.documents.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>, JpaSpecificationExecutor<Receipt> {
}