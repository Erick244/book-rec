package com.bookrec.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrec.app.models.dto.interests.CreateInterestDto;
import com.bookrec.app.models.entities.Interest;
import com.bookrec.app.services.InterestsService;

@RestController
@RequestMapping("/interests")
public class InterestsController {

	@Autowired
	private InterestsService interestsService;
	
	@GetMapping
	public ResponseEntity<Iterable<Interest>> findAll() {
		return this.interestsService.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody CreateInterestDto createInterestDto) {
		return this.interestsService.create(createInterestDto);
	}
}
