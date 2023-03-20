package com.search.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogVO {

	@JsonProperty("documents")
	private List<Document> documents;

	@JsonProperty("meta")
	private Meta meta;

	@Getter
	@Setter
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Document {

		@JsonProperty("title")
		private String title;

		@JsonProperty("contents")
		private String contents;

		@JsonProperty("url")
		private String url;

		@JsonProperty("datetime")
		private String datetime;

		@JsonProperty("blogname")
		private String blogname;

		@JsonProperty("thumbnail")
		private String thumbnail;

		@Override
		public String toString() {
			return "Document{" + "title='" + title + '\'' + ", contents='" + contents + '\'' + ", url='" + url + '\''
					+ ", datetime='" + datetime + '\'' + ", blogname='" + blogname + '\'' + ", thumbnail='" + thumbnail
					+ '\'' + '}';
		}
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

	@Override
	public String toString() {
		return "KakaoBlogVO{" + "documents=" + documents + ", meta=" + meta + '}';
	}
}