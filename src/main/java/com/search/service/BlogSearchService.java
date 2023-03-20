package com.search.service;

import com.search.page.Page;
import com.search.vo.BlogItemVO;

import reactor.core.publisher.Mono;

/**
 * 블로그 검색 및 검색어 저장 기능 제공하는 인터페이스
 * 다른 엔진 블로그 검색 추가시 참조
 * 
 * @author KRH
 *
 */
public interface BlogSearchService {
	
	// 블로그 검색
	Mono<Page<BlogItemVO>> searchBlog(String query, String sort, int page, int size);
	
	// 검색어 조회수 추가 및 저장
	void increaseCountOrInsert(String query);
    
}