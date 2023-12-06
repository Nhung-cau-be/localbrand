package com.localbrand.dal.dao.impl;

import com.localbrand.dal.dao.IOrderDao;
import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Customer;
import com.localbrand.dal.entity.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderDao extends GenericDao implements IOrderDao {
    @Override
    public SearchResult<List<Order>> search(Map<String, Object> search) {
        var searchResult = new SearchResult<List<Order>>();

        Session session = getSession();

        String orderQuery = "select * from `order` e ";
        var countTotalRecords = "select count(*) from ";

        List<String> conditions = new ArrayList<>();
        String code = search.get("code") != null ? (String) search.get("code") : null;
        if (code != null && !code.isEmpty()) {
            conditions.add("(e.code LIKE :code or e.name LIKE :code)");
            search.put("code", "%" + code + "%");
        }

        String createdDate = search.get("createdDate") != null ? (String) search.get("createdDate") : null;
        if (createdDate != null && !createdDate.isEmpty()) {
            conditions.add("DATE(e.created_date) = :createdDate");
        }

        String orderWhereStr = conditions.size() > 0 ? "where " + String.join(" and ", conditions) : "";
        orderQuery = orderQuery + " " + orderWhereStr;
        Query<Order> query = session.createNativeQuery(orderQuery, Order.class);
        Query countQuery = session.createNativeQuery(countTotalRecords + "(" + orderQuery + " ) as r");
        query.setProperties(search);
        countQuery.setProperties(search);

        int start = search.get("currentPage") != null ? (int) search.get("currentPage") : 0;
        int size = search.get("recordOfPage") != null ? (int) search.get("recordOfPage") : 0;
        if (start >= 0 && size > 0) {
            query.setFirstResult(start * size);
            query.setMaxResults(size);
        }

        List<Order> resultRows = query.getResultList();
        List<Order> result = new ArrayList<>(resultRows);

        long totalRecord = Long.parseLong((countQuery.uniqueResult()).toString());
        searchResult.setResult(result);
        searchResult.setTotalRecords(totalRecord);

        return searchResult;
    }
}
