package com.bookrec.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrec.app.models.dto.users.SetUserInterestsDto;
import com.bookrec.app.models.entities.Book;
import com.bookrec.app.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UsersService usersService;

	@PostMapping("/setInterests")
	public ResponseEntity<?> setInterests(@RequestBody SetUserInterestsDto setUserInterestsDto) {
		return this.usersService.setUserInterests(setUserInterestsDto);
	}
	
	@PostMapping("/readBook/{bookId}")
	public ResponseEntity<?> readBook(@PathVariable Long bookId) {
		return this.usersService.readBook(bookId);
	}
	
	@GetMapping("/booksRead")
	public ResponseEntity<List<Book>> booksRead() { 
		return this.usersService.booksRead();
	}
}
