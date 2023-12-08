package com.localbrand.service.impl;

import com.localbrand.dal.entity.Collection;
import com.localbrand.dal.repository.ICollectionRepository;
import com.localbrand.dal.repository.IProductRepository;
import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CollectionDto;
import com.localbrand.mappers.ICollectionDtoMapper;
import com.localbrand.service.ICollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionServiceImpl implements ICollectionService {
    private static final Logger logger = LoggerFactory.getLogger(CollectionServiceImpl.class);

    @Autowired
    private ICollectionRepository collectionRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<CollectionDto> getAll() {
        List<Collection> collections = collectionRepository.findAll();
        List<CollectionDto> collectionDtos = ICollectionDtoMapper.INSTANCE.toCollectionDtos(collections);

        return  collectionDtos;
    }

    @Override
    public BaseSearchDto<List<CollectionDto>> findAll(BaseSearchDto<List<CollectionDto>> searchDto) {
        if (searchDto == null || searchDto.getCurrentPage() == -1 || searchDto.getRecordOfPage() == 0) {
            searchDto.setResult(this.getAll());
            return searchDto;
        }

        if (searchDto.getSortBy() == null || searchDto.getSortBy().isEmpty()) {
            searchDto.setSortBy("name");
        }
        Sort sort = searchDto.isSortAsc() ? Sort.by(Sort.Direction.ASC, searchDto.getSortBy()) : Sort.by(Sort.Direction.DESC, searchDto.getSortBy());

        Pageable pageable = PageRequest.of(searchDto.getCurrentPage(), searchDto.getRecordOfPage(), sort);

        Page<Collection> page = collectionRepository.findAll(pageable);
        searchDto.setTotalRecords(page.getTotalElements());
        searchDto.setResult(ICollectionDtoMapper.INSTANCE.toCollectionDtos(page.getContent()));

        return searchDto;
    }

    @Override
    public CollectionDto getById(String id) {
        Collection collection = collectionRepository.findById(id).orElse(null);
        return ICollectionDtoMapper.INSTANCE.toCollectionDto(collection);
    }

    @Override
    public CollectionDto insert(CollectionDto collectionDto) {
        try {
            collectionDto.setId(UUID.randomUUID().toString());
            Collection collection = ICollectionDtoMapper.INSTANCE.toCollection(collectionDto);
            collectionRepository.save(collection);

            return collectionDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CollectionDto update(CollectionDto collectionDto) {
        try {
            Collection collection = ICollectionDtoMapper.INSTANCE.toCollection(collectionDto);
            collectionRepository.save(collection);

            return collectionDto;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean deleteById(String id) {
        try {
            collectionRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isUsing(String id) {
        return productRepository.countByCollectionId(id) > 0;
    }
}
