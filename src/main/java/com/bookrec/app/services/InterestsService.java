package com.bookrec.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bookrec.app.models.dto.interests.CreateInterestDto;
import com.bookrec.app.models.entities.Interest;
import com.bookrec.app.models.repositories.InterestRepository;

@Service
public class InterestsService {

	@Autowired
	private InterestRepository interestRepository;
	
	public ResponseEntity<Iterable<Interest>> findAll() {
		Iterable<Interest> interests = this.interestRepository.findAll();
		
		return ResponseEntity.ok(interests);
	}
	
	public ResponseEntity<?> create(CreateInterestDto createInterestDto) {
		String name = createInterestDto.name();
		
		Interest newInterest = new Interest(name);
		this.interestRepository.save(newInterest);
		
		return ResponseEntity.noContent().build();
	}
}
