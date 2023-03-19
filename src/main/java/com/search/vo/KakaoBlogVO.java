package com.search.vo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoBlogVO {
    
    @JsonProperty("title")
    private String title;

    @JsonProperty("url")
    private String url;

    @JsonProperty("contents")
    private String contents;

    @JsonProperty("datetime")
    private String datetime;

    @JsonProperty("blogname")
    private String blogname;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @Override
    public String toString() {
        return "Page{" +
                "title=" + title +
                ", contents=" + contents +
                ", url=" + url +
                ", datetime=" + datetime +
                '}';
    }
}