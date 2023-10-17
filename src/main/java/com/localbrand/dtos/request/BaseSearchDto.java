package com.localbrand.dtos.request;

import lombok.Data;

@Data
public class BaseSearchDto<T> {
    private int currentPage;
    private int recordOfPage;
    private long totalRecords;
    private boolean sortAsc = true;
    private String sortBy;
    private int pagingRange;
    private T result;
}
