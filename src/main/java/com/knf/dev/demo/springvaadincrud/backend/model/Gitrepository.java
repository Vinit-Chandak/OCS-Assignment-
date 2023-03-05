package com.knf.dev.demo.springvaadincrud.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GitrepositoryTable")
public class Gitrepository {
	@Id
	@GeneratedValue
	private Long id;
	private String gitrepositoryName;
	private String numberOfForks;

	protected Gitrepository() {
	}

	public Gitrepository(String gitrepositoryName, String numberOfForks) {
		this.gitrepositoryName = gitrepositoryName;
		this.numberOfForks = numberOfForks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getGitrepositoryName() {
		return gitrepositoryName;
	} 

	public void setGitrepositoryName(String gitrepositoryName) {
		this.gitrepositoryName = gitrepositoryName;
	}

	public String getNumberOfForks() {
		return numberOfForks;
	}

	public void setNumberOfForks(String numberOfForks) {
		this.numberOfForks = numberOfForks;
	}
}