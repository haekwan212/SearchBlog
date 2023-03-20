package com.search.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverBlogVO {
	
	@JsonProperty("total")
    private int total;           // 총 검색 결과 개수
	@JsonProperty("start")
    private int start;           // 검색 시작 위치(페이지)
	@JsonProperty("display")
    private int display;         // 한 번에 표시할 검색 결과 개수(size)
    
    @JsonProperty("items")
	private List<item> items;
    
    @Getter
	@Setter
	@ToString
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class item {
        // 블로그 포스트의 제목
		@JsonProperty("title")
		private String title;

		// 블로그 포스트의 URL
		@JsonProperty("link")
		private String link;

		// 블로그 포스트의 내용을 요약한 패시지 정보. 
		@JsonProperty("description")
		private String description;

		// 	블로그 포스트가 있는 블로그의 이름
		@JsonProperty("bloggername")
		private String bloggername;

		// 블로그 포스트가 있는 블로그의 주소
		@JsonProperty("bloggerlink")
		private String bloggerlink;

		// 블로그 포스트가 작성된 날짜
		@JsonProperty("postdate")
		private String postdate;

	}
}