package com.knf.dev.demo.springvaadincrud.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SearchTable")
public class Search {
	@Id
	@GeneratedValue
	private Long id;
	private String organizationName;
	private String topRepositories;
	private String oldestForks;

	protected Search() {
	}

	public Search(String organizationName, String topRepositories, String oldestForks) {
		this.organizationName = organizationName;
		this.topRepositories = topRepositories;
		this.oldestForks = oldestForks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTopRepositories() {
		return topRepositories;
	}

	public void setTopRepositories(String topRepositories) {
		this.topRepositories = topRepositories;
	}

	public String getOldestForks() {
		return oldestForks;
	}

	public void setOldestForks(String oldestForks) {
		this.oldestForks = oldestForks;
	}	
}