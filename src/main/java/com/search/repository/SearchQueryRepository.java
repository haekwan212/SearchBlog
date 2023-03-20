package com.search.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.search.entity.SearchQueryEntity;

@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQueryEntity, String> {
	
    Optional<SearchQueryEntity> findByQueryIgnoreCase(String query); // 검색어 저장

    List<SearchQueryEntity> findTop10ByOrderByCountDesc(); // 인기 검색어 10개 조회

}