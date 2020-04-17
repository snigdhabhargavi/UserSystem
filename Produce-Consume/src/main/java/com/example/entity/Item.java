package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
	
	private String name;
    private String html_url;
    private int watchers_count;
    private String language;
    private String description;
    
//    public Item() {
//		super();
//	}
//    
//    @JsonCreator
//	public Item(@JsonProperty String name,@JsonProperty String html_url,@JsonProperty int watchers_count,@JsonProperty String language,@JsonProperty String description) {
//		super();
//		this.name = name;
//		this.html_url = html_url;
//		this.watchers_count = watchers_count;
//		this.language = language;
//		this.description = description;
//	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	public int getWatchers_count() {
		return watchers_count;
	}
	public void setWatchers_count(int watchers_count) {
		this.watchers_count = watchers_count;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", html_url=" + html_url + ", watchers_count=" + watchers_count + ", language="
				+ language + ", description=" + description + "]";
	}
}
