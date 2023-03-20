package com.search.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogItemVO {

	private String blogName; 	// 블로그의 이름
	private String description; // 블로그 글 요약
	private String title; 		// 블로그 글 제목
	private String url;			// 블로그 글 URL
	private String dateTime; 	// 블로그 글 작성시간

}

