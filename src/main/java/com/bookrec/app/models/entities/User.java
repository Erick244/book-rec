package com.bookrec.app.models.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookrec.app.models.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 20)
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private UserRole role = UserRole.ADMIN;
	
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Interest> intrests = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Book> booksRead = new ArrayList<>();
	
	public User() {
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public List<Book> getBooksRead() {
		return booksRead;
	}
	
	public void addBookRead(Book book) {
		booksRead.add(book);
	}
	
	public void addInterest(Interest interest) {
		intrests.add(interest);
	}
	
	public boolean isAdmin() {
		return role == UserRole.USER;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
		SimpleGrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_USER");
		
		List<SimpleGrantedAuthority> adminRoles = List.of(adminRole, userRole);
		List<SimpleGrantedAuthority> userRoles = List.of(userRole);
		
		if (role == UserRole.ADMIN) {
			return adminRoles;
		} else {	
			return userRoles;
		}		
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
