package com.localbrand.dal.dao.impl;

import com.localbrand.dal.dao.IProductDao;
import com.localbrand.dal.data.SearchResult;
import com.localbrand.dal.entity.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductDao extends GenericDao implements IProductDao {
    @Override
    public SearchResult<List<Product>> search(Map<String, Object> search) {
        var searchResult = new SearchResult<List<Product>>();

        Session session = getSession();

        String productQuery = "select * from product e ";
        var countTotalRecords = "select count(*) from ";

        List<String> conditions = new ArrayList<>();
        String code = search.get("code") != null ? (String) search.get("code") : null;
        if (code != null && !code.isEmpty()) {
            conditions.add("(e.code LIKE :code or e.name LIKE :code)");
            search.put("code", "%" + code + "%");
        }

        String productGroupId = search.get("productGroupId") != null ? (String) search.get("productGroupId") : null;
        if (productGroupId != null && !productGroupId.isEmpty()) {
            conditions.add("e.product_group_id = :productGroupId");
        }

        String productWhereStr = conditions.size() > 0 ? "where " + String.join(" and ", conditions) : "";
        productQuery = productQuery + " " + productWhereStr;
        Query<Product> query = session.createNativeQuery(productQuery, Product.class);
        Query countQuery = session.createNativeQuery(countTotalRecords + "(" + productQuery + " ) as r");
        query.setProperties(search);
        countQuery.setProperties(search);

        int start = search.get("currentPage") != null ? (int) search.get("currentPage") : 0;
        int size = search.get("recordOfPage") != null ? (int) search.get("recordOfPage") : 0;
        if (start >= 0 && size > 0) {
            query.setFirstResult(start * size);
            query.setMaxResults(size);
        }

        List<Product> resultRows = query.getResultList();
        List<Product> result = new ArrayList<>(resultRows);

        long totalRecord = Long.parseLong((countQuery.uniqueResult()).toString());
        searchResult.setResult(result);
        searchResult.setTotalRecords(totalRecord);

        return searchResult;
    }
}
