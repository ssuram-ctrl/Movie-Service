package com.RatingService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RatingService.Rating;
import com.RatingService.RatingRequest;
import com.RatingService.RatingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	RatingService service;
	
	@GetMapping("/{name}")
	public ResponseEntity<Rating> getRating(@PathVariable String name){
		Rating rating = service.fetchRating(name);
		log.info("Returning rating for moive: "+ name);
		return ResponseEntity.ok(rating);
	}
	
	@PostMapping
	public ResponseEntity<Rating> updateRating(@RequestBody RatingRequest request) {
		Rating rating = service.updateAverage(request.getName(), request.getStars());
		log.info("Returning newAverage of movie: "+ request.getName());
		return ResponseEntity.ok(rating);
		
	}
}
