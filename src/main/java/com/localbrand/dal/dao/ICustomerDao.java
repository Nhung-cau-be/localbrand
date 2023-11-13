package com.localbrand.dal.dao;

import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Customer;

import java.util.List;
import java.util.Map;

public interface ICustomerDao {
    SearchResult<List<Customer>> search(Map<String, Object> search);
}
