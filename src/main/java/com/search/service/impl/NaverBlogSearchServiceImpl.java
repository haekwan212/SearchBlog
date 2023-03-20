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
import com.search.vo.NaverBlogVO;

import reactor.core.publisher.Mono;

@Service
public class NaverBlogSearchServiceImpl implements BlogSearchService{

    @Autowired
    private SearchQueryRepository searchQueryRepository;
    
	private final WebClient webClient;
	
    private static final String NAVER_SERVER_BLOG_SEARCH_API  = "https://openapi.naver.com/v1/search/blog";

    public NaverBlogSearchServiceImpl(@Value("${naver.client.id}") String naverClientId, @Value("${naver.client.secret}") String naverClientSecret, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
        .baseUrl(NAVER_SERVER_BLOG_SEARCH_API)
        .defaultHeader("X-Naver-Client-Id",  naverClientId )
        .defaultHeader("X-Naver-Client-Secret", naverClientSecret )
        .build();
    }
    
    public Mono<Page<BlogItemVO>> searchBlog(String query, String sort, int page, int size) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query"		, query) 	//검색어
                        .queryParam("display"	, size)		// 한페이지에 보여줄 글 수
                        .queryParam("sort"		, sort) 	// sim :정확도, date(
                        .queryParam("start"		, page) 	// 검색 시작 위치
                        .build())
                .retrieve()
                .bodyToMono(NaverBlogVO.class)
                .map(naverBlogVO -> {
                    List<BlogItemVO> blogItemList = naverBlogVO.getItems().stream().map(item -> {
                        BlogItemVO blogItem = new BlogItemVO();
                        blogItem.setBlogName(item.getBloggername());
                        blogItem.setDescription(item.getDescription());
                        blogItem.setTitle(item.getTitle());
                        blogItem.setUrl(item.getLink());
                        blogItem.setDateTime(item.getPostdate());
                        return blogItem;
                    }).collect(Collectors.toList());
                    
                    // 검색어 조회수 저장 
                    increaseCountOrInsert(query);
                    
                    // 총 페이지 수 계산
                    int totalPage = (int) Math.ceil((double) naverBlogVO.getTotal() / size);
                    // 마지막 페이지인지 확인
                    boolean isLastPage = page >= totalPage;
                    
                    return new Page<BlogItemVO>(blogItemList, page, size, naverBlogVO.getTotal(), isLastPage);
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(e);
                });
    }

	public void increaseCountOrInsert(String query) {
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