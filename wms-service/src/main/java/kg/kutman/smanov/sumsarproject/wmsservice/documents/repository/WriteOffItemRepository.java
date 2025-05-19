package kg.kutman.smanov.sumsarproject.wmsservice.documents.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOff;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffItemRepository extends JpaRepository<WriteOffItem, Long>, JpaSpecificationExecutor<WriteOffItem> {

    Page<WriteOffItem> findAllByWriteOff(WriteOff writeOff, Pageable pageable);
}