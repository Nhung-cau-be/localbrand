package com.localbrand.dal.dao.impl;

import com.localbrand.dal.dao.ICustomerDao;
import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomerDao extends GenericDao implements ICustomerDao {
    @Override
    public SearchResult<List<Customer>> search(Map<String, Object> search) {
        var searchResult = new SearchResult<List<Customer>>();

        Session session = getSession();

        String customerQuery = "select * from customer e ";
        var countTotalRecords = "select count(*) from ";

        List<String> conditions = new ArrayList<>();
        String name = search.get("name") != null ? (String) search.get("name") : null;
        if (name != null && !name.isEmpty()) {
            conditions.add("(e.name LIKE :name)");
            search.put("name", "%" + name + "%");
        }

        String customerTypeId = search.get("customerTypeId") != null ? (String) search.get("customerTypeId") : null;
        if (customerTypeId != null && !customerTypeId.isEmpty()) {
            conditions.add("e.customer_type_id = :customerTypeId");
        }

        String customerWhereStr = conditions.size() > 0 ? "where " + String.join(" and ", conditions) : "";
        customerQuery = customerQuery + " " + customerWhereStr;
        Query<Customer> query = session.createNativeQuery(customerQuery, Customer.class);
        Query countQuery = session.createNativeQuery(countTotalRecords + "(" + customerQuery + " ) as r");
        query.setProperties(search);
        countQuery.setProperties(search);

        int start = search.get("currentPage") != null ? (int) search.get("currentPage") : 0;
        int size = search.get("recordOfPage") != null ? (int) search.get("recordOfPage") : 0;
        if (start >= 0 && size > 0) {
            query.setFirstResult(start * size);
            query.setMaxResults(size);
        }

        List<Customer> resultRows = query.getResultList();
        List<Customer> result = new ArrayList<>(resultRows);

        long totalRecord = Long.parseLong((countQuery.uniqueResult()).toString());
        searchResult.setResult(result);
        searchResult.setTotalRecords(totalRecord);

        return searchResult;
    }
}
