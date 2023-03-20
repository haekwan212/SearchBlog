package com.search.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "search_query")
public class SearchQueryEntity {
    
    @Id
    private String query;
    
    @Column(nullable = false)
    private Long count = 0L; // deafult 값 0 설정
    
}