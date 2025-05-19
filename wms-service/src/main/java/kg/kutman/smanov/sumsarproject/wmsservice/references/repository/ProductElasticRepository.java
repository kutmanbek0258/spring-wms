package kg.kutman.smanov.sumsarproject.wmsservice.references.repository;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ProductElastic, Long> {
    Page<ProductElastic> findFuzzyByNameOrDescription(
        String name,
        String description,
        Pageable pageable
    );
}
