package com.search.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.search.entity.SearchQueryEntity;
import com.search.page.Page;
import com.search.service.InquireSearchQueryService;
import com.search.service.KakaoSearchService;
import com.search.service.NaverSearchService;
import com.search.vo.BlogItemVO;

import reactor.core.publisher.Mono;

@RestController
public class SearchController {

	private final InquireSearchQueryService inquireSearchQueryService; // 인기 검색어 조회 서비스
    private final KakaoSearchService kakaoSearchService; // 카카오 블로그 검색 API 서비스
    private final NaverSearchService naverSearchService; // 네이버 블로그 검색 API 서비스

    public SearchController(KakaoSearchService kakaoSearchService, NaverSearchService naverSearchService, InquireSearchQueryService inquireSearchQueryService) {
        this.kakaoSearchService = kakaoSearchService;
        this.inquireSearchQueryService = inquireSearchQueryService;
        this.naverSearchService = naverSearchService;
    }
	
    /**
     * 블로그 글 검색 결과 반환
     * 
     * @param query 검색어
     * @param type 검색 블로그 타입(1: Kakao 2: Naver)
     * @param sort 검색 정렬 방식(1: accuracy(정확도순) 2: recency(최신순))
     * @param page 결과 페이지 번호, 1~50 사이의 값, 기본 값 1
     * @param size 한 페이지에 보여질 문서 수한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
     * 
     * @return JSON 방식으로 PAGE 객체 리턴
     */
    @RequestMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<Page<BlogItemVO>>> search(@RequestParam("query") String query,
    					 @RequestParam(value = "type", defaultValue = "1") int type, // 1: Kakao 2: Naver
                         @RequestParam(value = "sort", defaultValue = "1") int sort, // 1: accuracy(정확도순) 2: recency(최신순)
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) {
    	
        // query 파라미터는 필수값이므로 빈 값인 경우 예외 처리
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("query parameter is required");
        }
        // page 최대값 50으로 제한 예외 처리
        if (page > 50) {
            throw new IllegalArgumentException("page is more than max");
        }
        // size 최대값 50으로 제한 예외 처리
        if (size > 50) {
            throw new IllegalArgumentException("page is more than max");
        }
        
        // sort
        if (sort != 1 && sort != 2) {
            throw new IllegalArgumentException("Invalid sort value: " + sort);
        }
        
        Mono<Page<BlogItemVO>> resultPage = null;
        
        String sortStr ="";
        
        switch (type) {
            case 1: // 카카오 블로그 검색 API 
				if (sort == 1) {
					sortStr = "accuracy"; // 정확도순
				} else if (sort == 2) {
					sortStr = "recency"; // 최신순
				}
				
                resultPage = kakaoSearchService.searchBlog(query, sortStr, page, size);
                break;
            case 2: // 네이버 블로그 검색 API
				if (sort == 1) {
					sortStr = "sim"; // 정확도순
				} else if (sort == 2) {
					sortStr = "date"; // 최신순
				}
				
                resultPage = naverSearchService.searchBlog(query, sortStr, page, size);
                
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + type);
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultPage);
    }
    
    /**
     * 인기 검색어 상위 10개를 조회
     * @return List<SearchQueryEntity>
     */
	@RequestMapping(value = "inquireSearchQuery", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SearchQueryEntity>> inquireSearchQueryTop10(){
    
    List<SearchQueryEntity> resultList = inquireSearchQueryService.inquireSearchQuery();
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultList);
    }
    
}