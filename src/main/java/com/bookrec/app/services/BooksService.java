package com.bookrec.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookrec.app.exceptions.BookrecHttpException;
import com.bookrec.app.exceptions.books.BookInterestsIsFullException;
import com.bookrec.app.exceptions.books.PassedManyInterestsException;
import com.bookrec.app.exceptions.books.TheBookaAlreadyHasTheInterestException;
import com.bookrec.app.exceptions.interests.InterestNotFoundException;
import com.bookrec.app.models.dto.books.AddBookInterestsDto;
import com.bookrec.app.models.dto.books.CreateBookDto;
import com.bookrec.app.models.entities.Book;
import com.bookrec.app.models.entities.Interest;
import com.bookrec.app.models.entities.User;
import com.bookrec.app.models.repositories.BookRepository;
import com.bookrec.app.models.repositories.InterestRepository;

@Service
public class BooksService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private InterestRepository interestRepository;

	public ResponseEntity<?> create(CreateBookDto createBookDto) {
		String title = createBookDto.title();
		String author = createBookDto.author();
		String description = createBookDto.description();
		List<Long> interestsIds = createBookDto.interestsIds();
		
		int minInterests = 1;
		int maxInterests = 5;
		boolean interestsSizeNotIsValid = interestsIds.size() < minInterests || interestsIds.size() > maxInterests;

		if (interestsSizeNotIsValid) {
			return ResponseEntity.badRequest().body("Add a minimum of 1 interest and a maximum of 5");
		}

		try {
			Book newBook = new Book(title, author, description);

			for (Long interestId : interestsIds) {
				Interest interest = this.interestRepository.findById(interestId).orElse(null);

				if (interest == null) {
					throw new InterestNotFoundException(interestId);
				}

				newBook.addInterest(interest);
				this.bookRepository.save(newBook);

				interest.addBook(newBook);
				this.interestRepository.save(interest);
			}

			return ResponseEntity.noContent().build();
		} catch (BookrecHttpException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}

	public ResponseEntity<Book> findOne(Long bookId) {
		Book book = this.bookRepository.findById(bookId).orElse(null);

		if (book == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(book);
	}

	public ResponseEntity<?> addInterests(AddBookInterestsDto addBookInterestsDto) {
		Long bookId = addBookInterestsDto.bookId();
		List<Long> interestsIds = addBookInterestsDto.interestsIds();

		try {
			Book book = this.bookRepository.findById(bookId).orElse(null);
			if (book == null) {
				return ResponseEntity.notFound().build();
			}

			List<Interest> bookInterests = book.getInterests();
			int maxInterests = 5;
			int numberOfInterestsAvailable = maxInterests - bookInterests.size();
			boolean bookInterestsIsFull = numberOfInterestsAvailable == 0;
			
			if (bookInterestsIsFull) {
				throw new BookInterestsIsFullException();
			}
			
			boolean passedManyInterests = numberOfInterestsAvailable < interestsIds.size();
			if (passedManyInterests) {
				throw new PassedManyInterestsException(numberOfInterestsAvailable, bookInterests.size());
			}
			
			for (Long interestId : interestsIds) {
				Interest interest = this.interestRepository.findById(interestId).orElse(null);
				
				if (interest == null) {					
					throw new InterestNotFoundException(interestId);
				}
				
				boolean theBookAlreadyHasThisInterest = bookInterests.contains(interest);
				if (theBookAlreadyHasThisInterest) {
					throw new TheBookaAlreadyHasTheInterestException(interestId);
				}
				
				book.addInterest(interest);
				this.bookRepository.save(book);
				
				interest.addBook(book);
				this.interestRepository.save(interest);
			}
			
			return ResponseEntity.noContent().build();
		} catch (BookrecHttpException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}
	}
	
	public ResponseEntity<Iterable<Book>> userRecommendations(int page, int take) {
		Pageable pageable = PageRequest.of(page, take);
		
		User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Iterable<Book> books = this.bookRepository.findBooksByUserCommonInterests(userAuth.getId(), pageable);
		
		return ResponseEntity.ok(books);
	}
}
