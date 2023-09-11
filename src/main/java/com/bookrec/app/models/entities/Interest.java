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

@Entity(name = "interests")
public class Interest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 30)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> users = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Book> books = new ArrayList<>();
	
	public Interest() {
	}

	public Interest(String name) {
		super();
		this.name = name;
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
	
	public void addUser(User user) {
		users.add(user);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
}
