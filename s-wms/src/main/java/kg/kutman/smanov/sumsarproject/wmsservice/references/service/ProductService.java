package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import kg.kutman.smanov.sumsarproject.wmsservice.helper.CSVHelper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductCsvDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.ProductElasticMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.ProductMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.PriceTemplate;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductElastic;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductGroup;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.ProductElasticRepository;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final ProductElasticMapper productElasticMapper;
    private final ProductElasticRepository elasticRepository;

    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product entity = productMapper.toEntity(productDto);
        ProductDto newEntity = productMapper.toDto(repository.save(entity));
        ProductElastic elasticEntity = new ProductElastic();
        BeanUtils.copyProperties(newEntity, elasticEntity);
        elasticRepository.save(elasticEntity);
        return newEntity;
    }

    public void deleteById(Long id) {
        elasticRepository.deleteById(id);
        repository.deleteById(id);
    }

    public ProductDto findById(Long id) {
        return productMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<ProductDto> findByCondition(String keyword, Pageable pageable) {
        if(keyword != null){
            if(keyword.equals("reindex")){
                elasticRepository.deleteAll();
                List<Product> entities = repository.findAll();
                List<ProductElastic> elasticEntities = new ArrayList<>();
                for (Product entity : entities) {
                    ProductElastic elastic = new ProductElastic();
                    BeanUtils.copyProperties(entity, elastic);
                    elasticEntities.add(elastic);
                }
                elasticRepository.saveAll(elasticEntities);
            }
            Page<ProductElastic> productElasticPage = elasticRepository.findFuzzyByNameOrDescription(keyword, keyword, pageable);
            List<ProductElastic> productElasticList = productElasticPage.getContent();
            return new PageImpl<>(productElasticMapper.toDto(productElasticList), pageable, productElasticPage.getTotalElements());
        }
        Page<Product> entityPage = repository.findAll(pageable);
        List<Product> entities = entityPage.getContent();
        return new PageImpl<>(productMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    @Transactional
    public ProductDto update(ProductDto productDto, Long id) {
        ProductDto data = findById(id);
        Product entity = productMapper.toEntity(productDto);
        BeanUtils.copyProperties(entity, data, "id", "quantity", "price", "cost");
        return save(data);
    }

    @Transactional
    public void saveMany(MultipartFile file){
        List<ProductCsvDto> productListCsv = CSVHelper.convertToModel(file, ProductCsvDto.class);
        List<Product> products = new ArrayList<>();
        List<ProductElastic> productElasticList = new ArrayList<>();
        for (ProductCsvDto productCsvDto : productListCsv) {
            Product product = new Product(
                new PriceTemplate(productCsvDto.getPriceTemplateID()),
                new ProductGroup(productCsvDto.getProductGroupID())
            );
            BeanUtils.copyProperties(productCsvDto, product, "priceTemplate", "productGroup");
            products.add(product);
        }
        List<Product> savedProducts = repository.saveAll(products);
        for (Product savedProduct: savedProducts){
            ProductElastic productElastic = new ProductElastic();
            BeanUtils.copyProperties(savedProduct, productElastic);
            productElasticList.add(productElastic);
        }
        log.debug("elastic product list:" + productElasticList);
        elasticRepository.saveAll(productElasticList);
    }
}