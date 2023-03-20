package com.search.page;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page<T> implements Serializable{ //직렬화
    private List<T> item; 	// 블로그 글 목록
    private int page; 		// 현재 페이지
    private int size; 		// 현재 보여주는 글 수
    private int totalCount; // 검색된 총 갯수
    private boolean lastPage; // 마지막 페이지 여부
    
    public Page(List<T> item, int pageNumber, int pageSize, int totalCount, boolean lastPage) {
        this.item = item;
        this.page = pageNumber;
        this.size = pageSize;
        this.totalCount = totalCount;
        this.lastPage = lastPage;
    }
    
}