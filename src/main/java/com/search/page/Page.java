package com.search.page;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page<T> implements Serializable{ //직렬화
    private List<T> item;
    private int pageNumber;
    private int pageSize;
    private int totalCount;
    private boolean nextEnd;
    
    public Page(List<T> item, int pageNumber, int pageSize, int totalCount, boolean nextEnd) {
        this.item = item;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.nextEnd = nextEnd;
    }
    
}