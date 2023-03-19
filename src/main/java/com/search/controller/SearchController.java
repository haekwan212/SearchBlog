package com.search.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.search.page.Page;
import com.search.service.KakaoSearchService;

@RestController
public class SearchController {

    private final KakaoSearchService kakaoSearchService;
//    private final NaverSearchService naverSearchService;

    public SearchController(KakaoSearchService kakaoSearchService) {
        this.kakaoSearchService = kakaoSearchService;
//        this.naverSearchService = naverSearchService;
    }
	
    
    @PostMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<?>> search(@RequestParam("query") String query,
    					 @RequestParam(value = "type", defaultValue = "1") int type, // 1: Kakao 2: Naver
                         @RequestParam(value = "sort", defaultValue = "1") int sort, // 1: accuracy(정확도순) 2: recency(최신순)
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) {
    	
        // query 파라미터는 필수값이므로 빈 값인 경우 예외 처리
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("query parameter is required");
        }
        
        Page<?> resultPage = null;
        
        switch (type) {
            case 1:
                resultPage = kakaoSearchService.searchBlog(query, sort, page, size);
                break;
            case 2:
                //resultPage = naverSearchService.searchBlog(query, sort, page, size);
                break;
            default:
                throw new IllegalArgumentException("Invalid search type: " + type);
        }
        
        resultPage.to1String();
        //System.out.println("########### resultPage : " + resultPage.toString());
        
//        return ResponseEntity.ok(resultPage);
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resultPage);
    }
    
    @PostMapping(value = "search2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> search2(@RequestParam("query") String query,
    					 @RequestParam(value = "type", defaultValue = "1") int type, // 1: Kakao 2: Naver
                         @RequestParam(value = "sort", defaultValue = "1") int sort, // 1: accuracy(정확도순) 2: recency(최신순)
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) {
    	
        // query 파라미터는 필수값이므로 빈 값인 경우 예외 처리
        if (query == null || query.isEmpty()) {
            throw new IllegalArgumentException("query parameter is required");
        }
        String dd = "rrrrrrrrrrr";
        
        return ResponseEntity.ok(dd);
    }
}