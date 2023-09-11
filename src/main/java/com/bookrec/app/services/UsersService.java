package com.bookrec.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookrec.app.exceptions.BookrecHttpException;
import com.bookrec.app.exceptions.interests.InterestNotFoundException;
import com.bookrec.app.models.dto.users.SetUserInterestsDto;
import com.bookrec.app.models.entities.Book;
import com.bookrec.app.models.entities.Interest;
import com.bookrec.app.models.entities.User;
import com.bookrec.app.models.repositories.BookRepository;
import com.bookrec.app.models.repositories.InterestRepository;
import com.bookrec.app.models.repositories.UserRepository;

@Service
public class UsersService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private InterestRepository interestRepository;
	
	@Autowired
	private BookRepository bookRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return user;
	}

	public ResponseEntity<?> setUserInterests(SetUserInterestsDto setUserInterestsDto) {

		try {
			List<Long> interestsIds = setUserInterestsDto.interestsIds();
			int minInterests = 3;
			
			if (interestsIds.size() < minInterests) {
				return ResponseEntity.badRequest().body("Select at least 3 interests");
			}
			 
			for (Long interestId : interestsIds) {
				Interest interest = this.interestRepository.findById(interestId).orElse(null);
				
				if (interest == null) {
					throw new InterestNotFoundException(interestId);			
				} 
				
				User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				userAuth.addInterest(interest);
				this.userRepository.save(userAuth);
				
				interest.addUser(userAuth);
				this.interestRepository.save(interest);
				
			}
			
			return ResponseEntity.noContent().build();

		} catch (BookrecHttpException e) {
			return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
		}

	}
	
	public ResponseEntity<?> readBook(Long bookId) {
		Book book = this.bookRepository.findById(bookId).orElse(null);
		
		if (book == null) {
			return ResponseEntity.notFound().build();
		}
		
		User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		userAuth.addBookRead(book);
		this.userRepository.save(userAuth);
		
		book.addReader(userAuth);
		this.bookRepository.save(book);
		
		return ResponseEntity.noContent().build();
	}
	
	public ResponseEntity<List<Book>> booksRead() {
		User userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Book> userBooksRead = userAuth.getBooksRead();
		
		return ResponseEntity.ok(userBooksRead);
	}
}
