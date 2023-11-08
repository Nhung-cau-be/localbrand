package com.localbrand.dal.dao;

import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Product;

import java.util.List;
import java.util.Map;

public interface IProductDao {
    SearchResult<List<Product>> search(Map<String, Object> search);
}
