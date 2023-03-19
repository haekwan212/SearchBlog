package com.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.search.page.Page;
import com.search.vo.KakaoBlogVO;

import reactor.core.publisher.Flux;

@Service
public class KakaoSearchService {

	private final WebClient webClient;
	
    private static final String KAKAO_SERVER_API  = "https://dapi.kakao.com";

    private static final String KAKAO_BLOG_SEARCH = "/v2/search/blog";
    
    public KakaoSearchService(@Value("${kakao.api.key}") String kakaoApiKey ,WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl(KAKAO_SERVER_API + KAKAO_BLOG_SEARCH)
        //.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey ) 
        .build();
    }
    
    public Page<KakaoBlogVO> searchBlog(String query, int sort, int page, int size) {
    	
    	Flux<KakaoBlogVO> flux = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("sort", sort==1?"accuracy":"recency")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(KakaoBlogVO.class);
        List<KakaoBlogVO> resultList = flux.collectList().block();
        
        System.out.println("########### resultList : " + resultList);
        
        int totalCount = resultList.size();
        int start = (page - 1) * size;
        int end = Math.min(start + size, totalCount);
        
        return new Page<KakaoBlogVO>(resultList.subList(start, end), page, size, totalCount);
    }

}