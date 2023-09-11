package com.bookrec.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookrec.app.models.dto.books.AddBookInterestsDto;
import com.bookrec.app.models.dto.books.CreateBookDto;
import com.bookrec.app.models.entities.Book;
import com.bookrec.app.services.BooksService;

@RestController
@RequestMapping("/books")
public class BooksController {
	
	@Autowired
	private BooksService booksService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CreateBookDto createBookDto) {
		return this.booksService.create(createBookDto);
	}
	
	@GetMapping("/findOne/{bookId}")
	public ResponseEntity<Book> findOne(@PathVariable Long bookId) {
		return this.booksService.findOne(bookId);
	}
	
	@PostMapping("/addInterests")
	public ResponseEntity<?> addInterests(@RequestBody AddBookInterestsDto addBookInterestsDto) {
		return this.booksService.addInterests(addBookInterestsDto);
	}
	
	@GetMapping("/userRecommendations")
	public ResponseEntity<Iterable<Book>> userRecommendations(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int take
	) {
		return this.booksService.userRecommendations(page, take);
	}
}
