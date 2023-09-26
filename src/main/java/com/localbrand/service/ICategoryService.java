package com.localbrand.service;

import java.util.List;

import com.localbrand.dal.entity.Category;
import com.localbrand.dtos.response.CategoryDto;

public interface ICategoryService {
	List<CategoryDto> getAll();
}
