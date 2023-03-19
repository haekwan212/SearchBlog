package com.search.page;

import java.util.List;

public class Page<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    
    public Page(List<T> content, int pageNumber, int pageSize, int totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
    
    public void to1String() {
    	for (T item : content) {
    	    System.out.println(item.toString());
    	}
    	System.out.println("pageNumber : " + pageNumber);
    	System.out.println("pageSize : " + pageSize);
    	System.out.println("totalElements : " + totalElements);
    }
}