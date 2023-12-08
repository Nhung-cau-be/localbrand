package com.localbrand.mappers;

import com.localbrand.dal.entity.Collection;
import com.localbrand.dal.entity.CustomerType;
import com.localbrand.dtos.response.CollectionDto;
import com.localbrand.dtos.response.CustomerTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ICollectionDtoMapper {
    ICollectionDtoMapper INSTANCE = Mappers.getMapper(ICollectionDtoMapper.class);

    CollectionDto toCollectionDto(Collection collection);

    List<CollectionDto> toCollectionDtos(List<Collection> collections);

    Collection toCollection(CollectionDto collectionDto);
}
