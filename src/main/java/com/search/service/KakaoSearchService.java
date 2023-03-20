package com.search.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.search.entity.SearchQueryEntity;
import com.search.page.Page;
import com.search.repository.SearchQueryRepository;
import com.search.vo.BlogItemVO;
import com.search.vo.KakaoBlogVO;

import reactor.core.publisher.Mono;

@Service
public class KakaoSearchService {

    @Autowired
    private SearchQueryRepository searchQueryRepository;
    
	private final WebClient webClient;
	
    private static final String KAKAO_SERVER_API  = "https://dapi.kakao.com";

    private static final String KAKAO_BLOG_SEARCH = "/v2/search/blog";
    
    public KakaoSearchService(@Value("${kakao.api.key}") String kakaoApiKey ,WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl(KAKAO_SERVER_API + KAKAO_BLOG_SEARCH)
        .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey ) 
        .build();
    }
    
    public Mono<Page<BlogItemVO>> searchBlog(String query, String sort, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("sort", sort)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToMono(KakaoBlogVO.class)
                .map(kakaoBlogVO -> {
                    List<BlogItemVO> blogItemList = kakaoBlogVO.getDocuments().stream().map(document -> {
                        BlogItemVO blogItem = new BlogItemVO();
                        blogItem.setBloggername(document.getBlogname());
                        blogItem.setDescription(document.getContents());
                        blogItem.setTitle(document.getTitle());
                        blogItem.setUrl(document.getUrl());
                        blogItem.setDatetime(document.getDatetime());
                        return blogItem;
                    }).collect(Collectors.toList());
                    
                    // 검색어 조회수 저장 
                    increaseCountOrInsert(query);
                    
                    return new Page<>(blogItemList, page, size, kakaoBlogVO.getMeta().getPageableCount(), kakaoBlogVO.getMeta().isEnd());
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (HttpStatus.NOT_FOUND.equals(e.getStatusCode())) {
                        return Mono.error(new InvalidParameterException("krh TODOs")); //TODO::
                    }
                    return Mono.error(e);
                });
    }

	private void increaseCountOrInsert(String query) {
		Optional<SearchQueryEntity> searchQueryOptional = searchQueryRepository.findByQueryIgnoreCase(query);
		
		if (searchQueryOptional.isPresent()) { // 검색어가 테이블에 존재 하면
			SearchQueryEntity searchQuery = searchQueryOptional.get();
			searchQuery.setCount(searchQuery.getCount() + 1L); // 조회수 1 증가
			searchQueryRepository.save(searchQuery);
		} else { // 존재 하지 않으면
			SearchQueryEntity searchQuery = new SearchQueryEntity();
			searchQuery.setQuery(query);
			searchQuery.setCount(1L); // count 1로 신규 생성 
			searchQueryRepository.save(searchQuery);
		}
	}
    
}