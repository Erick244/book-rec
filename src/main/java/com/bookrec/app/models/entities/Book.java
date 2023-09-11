package com.bookrec.app.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 50)
	private String title;
	
	@NotNull
	@Size(min = 3, max = 20)
	private String author;
	
	@Size(min = 0, max = 250)
	private String description = null;
	
	@ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
	private List<Interest> interests = new ArrayList<>();
	
	@ManyToMany(mappedBy = "booksRead", fetch = FetchType.EAGER)
	private List<User> readers = new ArrayList<>();
	
	public Book() {
	}

	public Book(String title, String author, String description) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
	}
	
	public void addInterest(Interest interest) {
		interests.add(interest);
	}
	
	public void addReader(User reader) {
		this.readers.add(reader);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Interest> getInterests() {
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public Long getId() {
		return id;
	}
}
