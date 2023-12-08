package com.localbrand.service;

import com.localbrand.dtos.request.BaseSearchDto;
import com.localbrand.dtos.response.CategoryDto;
import com.localbrand.dtos.response.CollectionDto;

import java.util.List;

public interface ICollectionService {
    List<CollectionDto> getAll();

    BaseSearchDto<List<CollectionDto>> findAll(BaseSearchDto<List<CollectionDto>> searchDto);

    CollectionDto getById(String id);

    CollectionDto insert(CollectionDto collectionDto);

    CollectionDto update(CollectionDto collectionDto);

    Boolean deleteById(String id);

    boolean isUsing(String id);
}
