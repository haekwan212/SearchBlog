package com.search.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogVO {

	@JsonProperty("documents")
	private List<Document> documents;

	@JsonProperty("meta")
	private Meta meta;

	@Getter
	@Setter
	@ToString
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Document {

		@JsonProperty("title") 
		private String title; 		// 블로그 글 제목

		@JsonProperty("contents")
		private String contents; 	// 블로그 글 요약

		@JsonProperty("url")
		private String url; 		// 블로그 글 URL

		@JsonProperty("datetime")
		private String datetime; 	//블로그 글 작성시간

		@JsonProperty("blogname")
		private String blogname; 	// 블로그의 이름
		
		@JsonProperty("thumbnail")
		private String thumbnail; 	// 검색 시스템에서 추출한 대표 미리보기 이미지 URL

	}

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Meta {

		@JsonProperty("total_count")
		private int totalCount;

		@JsonProperty("pageable_count")
		private int pageableCount;

		@JsonProperty("is_end")
		private boolean isEnd;

		@Override
		public String toString() {
			return "Meta{" + "totalCount=" + totalCount + ", pageableCount=" + pageableCount + ", isEnd=" + isEnd + '}';
		}
	}

}