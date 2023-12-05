package com.localbrand.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.localbrand.dal.dao.IProductDao;
import com.localbrand.dal.entity.*;
import com.localbrand.dal.repository.*;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.request.ProductSearchDto;
import com.localbrand.dtos.response.*;
import com.localbrand.mappers.*;
import com.localbrand.service.IProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProductImageRepository productImageRepository;
    @Autowired
    private IProductAttributeDetailRepository productAttributeDetailRepository;
    @Autowired
    private IProductSKURepository productSKURepository;
    @Autowired
    private IProductAttributeValueRepository productAttributeValueRepository;
    @Autowired
    private IProductAttributeRepository productAttributeRepository;
    @Autowired
    private IProductDao productDao;

    private int currentVariant = 1;

    @Override
    public List<ProductDto> getAll() {
        return IProductDtoMapper.INSTANCE.toProductDtos(productRepository.findAll());
    }

    @Override
    public BaseSearchDto<List<ProductDto>> findAll(BaseSearchDto<List<ProductDto>> searchDto) {
        if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("code");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Product> page = productRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(IProductDtoMapper.INSTANCE.toProductDtos(page.getContent()));

        return searchDto;
    }

    @Override
    public ProductSearchDto search(ProductSearchDto searchDto) {
        try {
            var oMapper = new ObjectMapper();
            Map<String, Object> map = oMapper.convertValue(searchDto, Map.class);

            if(searchDto.getAttributeValueIds() != null && !searchDto.getAttributeValueIds().isEmpty()) {
                List<ProductAttributeDetail> productAttributeDetails = productAttributeDetailRepository.getByAttributeValueIds(searchDto.getAttributeValueIds());
                List<String> productIds = productAttributeDetails.stream().map(ProductAttributeDetail::getProductSKU).map(ProductSKU::getProduct).map(Product::getId).collect(Collectors.toSet()).stream().toList();
                map.put("productIds", productIds);
            }

            searchDto.setMinPrice(searchDto.getMinPrice() == null ? 0 : searchDto.getMinPrice());
            searchDto.setMaxPrice(searchDto.getMaxPrice() == null ? 1000000 : searchDto.getMaxPrice());
            List<Product> products = productRepository.findAll();
            products = products.stream().filter(product -> {
                Integer price = product.getDiscountPrice() == null ? product.getPrice() : product.getDiscountPrice();
                return searchDto.getMinPrice() <= price && searchDto.getMaxPrice() >= price;
            }).collect(Collectors.toList());
            Set<String> productIds = map.get("productIds") == null ? new HashSet<>() : (Set<String>) map.get("productIds");
            productIds.addAll(products.stream().map(Product::getId).collect(Collectors.toSet()));
            map.put("productIds", productIds.stream().toList());

            var result = productDao.search(map);
            var productDtoList = IProductDtoMapper.INSTANCE.toProductDtos(result.getResult());

            searchDto.setTotalRecords(result.getTotalRecords());
            searchDto.setResult(productDtoList);

            return searchDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            searchDto.setResult(null);
            searchDto.setTotalRecords(0);

            return searchDto;
        }
    }

    @Override
    public ProductFullDto getFullById(String id) {
        try {
            Product product = productRepository.findById(id).orElse(null);

            if (product == null)
                return null;

            ProductFullDto productFullDto = IProductDtoMapper.INSTANCE.toProductFullDto(product);
            productFullDto.setImages(IProductImageDtoMapper.INSTANCE.toProductImageDtos(productImageRepository.getByProductId(id)));

            productFullDto.setProductSKUs(IProductSKUDtoMapper.INSTANCE.toProductSKUFullDtos(productSKURepository.getByProductId(id)));
            productFullDto.getProductSKUs().forEach(sku -> sku.setAttributeValues(
                    IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(
                            productAttributeDetailRepository.getByProductSKUId(sku.getId()).stream().map(ProductAttributeDetail::getProductAttributeValue).collect(Collectors.toList())
                    )));

            List<ProductAttributeDetail> productAttributeDetails = productAttributeDetailRepository.getByProductId(id);
            Set<ProductAttributeValue> productAttributeValues = productAttributeDetails.stream().map(ProductAttributeDetail::getProductAttributeValue).collect(Collectors.toSet());
            productFullDto.setAttributeValues(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValueDtos(productAttributeValues.stream().toList()));

            return productFullDto;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ProductFullDto insert(ProductFullDto productFullDto) {
        try {
            productFullDto.setId(UUID.randomUUID().toString());
            Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);

            productRepository.save(product);
            productFullDto = saveImages(productFullDto);
            productFullDto = saveSkus(productFullDto);

            return productFullDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public ProductFullDto update(ProductFullDto productFullDto) {
        try {
            Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);

            productRepository.save(product);
            productFullDto = saveImages(productFullDto);

            return productFullDto;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isExistCode(String code) {
        return productRepository.countByCode(code) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(String id) {
        try {
            Product product = productRepository.findById(id).orElse(null);
            if(product == null) {
                return true;
            }
            //TODO: Check đang sử dụng trước khi xóa

            productAttributeDetailRepository.deleteByProductId(id);
            productSKURepository.deleteByProductId(id);
            productImageRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace().toString());
            return false;
        }
    }

    private ProductFullDto saveImages(ProductFullDto productFullDto) {
        Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);
        productImageRepository.deleteByProductId(productFullDto.getId());
        List<ProductImage> images = IProductImageDtoMapper.INSTANCE.toProductImages(productFullDto.getImages());

        images.forEach(image -> {
            image.setId(UUID.randomUUID().toString());
            image.setProduct(product);
        });
        productImageRepository.saveAll(images);

        productFullDto.setImages(IProductImageDtoMapper.INSTANCE.toProductImageDtos(images));
        return productFullDto;
    }

    @Transactional
    private ProductFullDto saveSkus(ProductFullDto productFullDto) {
        Product product = IProductDtoMapper.INSTANCE.toProduct(productFullDto);
        if(productFullDto.getAttributeValues().isEmpty()) {
            ProductSKU productSKU = new ProductSKU();
            productSKU.setId(UUID.randomUUID().toString());
            productSKU.setCode(productFullDto.getCode() + "0");
            productSKU.setQuantity(0);
            productSKU.setProduct(product);
            productSKURepository.save(productSKU);

            productFullDto.setProductSKUs(List.of(IProductSKUDtoMapper.INSTANCE.toProductSKUFullDto(productSKU)));
            return productFullDto;
        }

        int totalVariant = 1;
        Set<ProductAttributeFullDto> productAttributeDtos = new HashSet<>();
        productFullDto.getAttributeValues().forEach(value -> productAttributeDtos.add(IProductAttributeDtoMapper.INSTANCE.toProductAttributeFullDto(value.getAttribute())));
        for(ProductAttributeFullDto productAttributeFullDto : productAttributeDtos) {
            List<ProductAttributeValueDto> values = new ArrayList<>();
            productFullDto.getAttributeValues().forEach(value -> {
                if(value.getAttribute().getId().equals(productAttributeFullDto.getId())) {
                    values.add(value);
                }
            });

            productAttributeFullDto.setValues(values);
            totalVariant *= values.size();
        }

//        int variant = 0;
//        while (variant < totalVariant) {
//            List<ProductAttributeValueDto> attributeValueDtos = new ArrayList<>();
//            for(ProductAttributeValueDto attributeValue : productFullDto.getAttributeValues()) {
//                if(isValidSKU(attributeValueDtos, attributeValue)) {
//                    attributeValueDtos.add(attributeValue);
//                }
//
//            }
//            if(attributeValueDtos.size() == productAttributeDtos.size()) {
//                variant++;
//                ProductSKU productSKU = new ProductSKU();
//                productSKU.setId(UUID.randomUUID().toString());
//                productSKU.setCode(productFullDto.getCode() + variant);
//                productSKU.setQuantity(0);
//                productSKU.setProduct(product);
//                productSKURepository.save(productSKU);
//
//                for(ProductAttributeValueDto value : attributeValueDtos) {
//                    ProductAttributeDetail productAttributeDetail = new ProductAttributeDetail();
//                    productAttributeDetail.setId(UUID.randomUUID().toString());
//                    productAttributeDetail.setProductSKU(productSKU);
//                    productAttributeDetail.setProductAttributeValue(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValue(value));
//                    productAttributeDetailRepository.save(productAttributeDetail);
//                }
//            }
//        }

        currentVariant = 1;
        createSKU(product, productAttributeDtos.stream().toList(), new ArrayList<>());

        return productFullDto;
    }

    @Transactional
    private void createSKU(Product product, List<ProductAttributeFullDto> productAttributeRemains, List<ProductAttributeValueDto> productAttributeValueDtos) {
        if(productAttributeRemains.size() == 0) {
            ProductSKU productSKU = new ProductSKU();
            productSKU.setId(UUID.randomUUID().toString());
            productSKU.setCode(product.getCode() + currentVariant++);
            productSKU.setQuantity(0);
            productSKU.setProduct(product);
            productSKURepository.save(productSKU);

            List<ProductAttributeDetail> productAttributeDetails = new ArrayList<>();
            for(ProductAttributeValueDto value : productAttributeValueDtos) {
                ProductAttributeDetail productAttributeDetail = new ProductAttributeDetail();
                productAttributeDetail.setId(UUID.randomUUID().toString());
                productAttributeDetail.setProductSKU(productSKU);
                productAttributeDetail.setProductAttributeValue(IProductAttributeValueDtoMapper.INSTANCE.toProductAttributeValue(value));
                productAttributeDetails.add(productAttributeDetail);
            }
            productAttributeDetailRepository.saveAll(productAttributeDetails);
        } else {
            ProductAttributeFullDto productAttributeFullDto = productAttributeRemains.get(0);

            for(ProductAttributeValueDto productAttributeValueDto : productAttributeFullDto.getValues()) {
                productAttributeValueDtos.removeIf(value -> value.getAttribute().getId().equals(productAttributeFullDto.getId()));
                productAttributeValueDtos.add(productAttributeValueDto);
                createSKU(product, productAttributeRemains.size()==1 ? new ArrayList<>() : productAttributeRemains.subList(1, productAttributeRemains.size()),productAttributeValueDtos);
            }
        }
    }

//    private boolean isValidSKU(List<ProductAttributeValueDto> attributeValueDtos, ProductAttributeValueDto attributeValueCurrent) {
//        for(ProductAttributeValueDto attributeValue : attributeValueDtos) {
//            if(attributeValue.getId().equals(attributeValueCurrent.getId()) || attributeValue.getAttribute().getId().equals(attributeValueCurrent.getAttribute().getId()))
//                return false;
//        }
//        return true;
//    }
}
