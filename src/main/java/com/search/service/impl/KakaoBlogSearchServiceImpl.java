package com.search.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.search.entity.SearchQueryEntity;
import com.search.page.Page;
import com.search.repository.SearchQueryRepository;
import com.search.service.BlogSearchService;
import com.search.vo.BlogItemVO;
import com.search.vo.KakaoBlogVO;

import reactor.core.publisher.Mono;

@Service
public class KakaoBlogSearchServiceImpl implements BlogSearchService{

    @Autowired
    private SearchQueryRepository searchQueryRepository;
    
	private final WebClient webClient;
	
    private static final String KAKAO_SERVER_BLOG_SEARCH_API  = "https://dapi.kakao.com/v2/search/blog";

    public KakaoBlogSearchServiceImpl(@Value("${kakao.api.key}") String kakaoApiKey ,WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl(KAKAO_SERVER_BLOG_SEARCH_API)
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
                        blogItem.setBlogName(document.getBlogname());
                        blogItem.setDescription(document.getContents());
                        blogItem.setTitle(document.getTitle());
                        blogItem.setUrl(document.getUrl());
                        blogItem.setDateTime(document.getDatetime());
                        return blogItem;
                    }).collect(Collectors.toList());
                    
                    // 검색어 조회수 저장 
                    increaseCountOrInsert(query);
                    
                    return new Page<BlogItemVO>(blogItemList, page, size, kakaoBlogVO.getMeta().getPageableCount(), kakaoBlogVO.getMeta().isEnd());
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(e);
                });
    }

	public void increaseCountOrInsert(String query) {
		Optional<SearchQueryEntity> searchQueryOptional = searchQueryRepository.findByQueryIgnoreCase(query);
		
		if (searchQueryOptional.isPresent()) { // 검색어가 테이블에 존재하는 경우
			SearchQueryEntity searchQuery = searchQueryOptional.get();
			searchQuery.setCount(searchQuery.getCount() + 1L); // 조회수 1 증가
			searchQueryRepository.save(searchQuery);
		} else { // 검색어가 테이블에 존재하지 않는 경우
			SearchQueryEntity searchQuery = new SearchQueryEntity();
			searchQuery.setQuery(query);
			searchQuery.setCount(1L); // count 1로 신규 생성 
			searchQueryRepository.save(searchQuery);
		}
	}
    
}