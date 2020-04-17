package com.example.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HottestRepositories {
	
	private Set<Item> items;

	@Override
	public String toString() {
		return "HottestRepositories [items=" + items + "]";
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

//	public HottestRepositories() {
//		super();
//	}
//
//	public HottestRepositories(@JsonProperty("items")Item[] items) {
//		super();
//		this.items = items;
//	}	
}
