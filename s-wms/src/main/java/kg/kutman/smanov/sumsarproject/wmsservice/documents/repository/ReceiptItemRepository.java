package kg.kutman.smanov.sumsarproject.wmsservice.documents.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.ReceiptItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiptItemRepository extends JpaRepository<ReceiptItem, Long>, JpaSpecificationExecutor<ReceiptItem> {
    Page<ReceiptItem> findByReceipt(Long receiptId, Pageable pageable);
}