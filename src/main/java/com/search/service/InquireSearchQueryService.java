package com.search.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.search.entity.SearchQueryEntity;
import com.search.repository.SearchQueryRepository;

@Service
public class InquireSearchQueryService {

	@Autowired
    private SearchQueryRepository searchQueryRepository;
	
	public List<SearchQueryEntity> inquireSearchQuery() {
		//List<SearchQueryEntity> resultList = searchQueryRepository.findTop10ByOrderByCountDesc();
		return searchQueryRepository.findTop10ByOrderByCountDesc();
    }
}
