package com.localbrand.dal.dao;

import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.Order;

import java.util.List;
import java.util.Map;

public interface IOrderDao {
    SearchResult<List<Order>> search(Map<String, Object> search);

}
