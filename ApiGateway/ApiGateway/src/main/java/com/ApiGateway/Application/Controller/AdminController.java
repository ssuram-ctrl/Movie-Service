package com.ApiGateway.Application.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.micrometer.observation.autoconfigure.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.ApiGateway.Application.Model.MovieEntity;
import com.ApiGateway.Application.Model.MovieRating;
import com.ApiGateway.Application.Model.Rating;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${movie-service.url}")
	private String movieServiceUrl;
	
	@Value("${rating-service.url}")
	private String ratingServiceUrl;
	
	@PostMapping
	public ResponseEntity<Object> addMovie(@RequestBody MovieEntity movie) {

		try {
			log.info("Adding Movie");
			MovieEntity savedMovie = restTemplate.postForObject(movieServiceUrl, movie, MovieEntity.class);
			return ResponseEntity.ok().body(savedMovie);
		} catch (HttpStatusCodeException ex) {
			log.error("Error adding movie:{}", ex.getMessage());
			return ResponseEntity.status(ex.getStatusCode()).contentType(MediaType.APPLICATION_JSON)
					.body(ex.getResponseBodyAsString());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateMovie(@PathVariable Long id,@RequestBody MovieEntity movie) {

		try {
			log.info("Adding Movie");
			restTemplate.put(movieServiceUrl + "/" + id, movie);
			return ResponseEntity.ok().build();
		} catch (HttpStatusCodeException ex) {
			log.error("Error adding movie:{}", ex.getMessage());
			return ResponseEntity.status(ex.getStatusCode()).contentType(MediaType.APPLICATION_JSON)
					.body(ex.getResponseBodyAsString());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> fetchMovieAndRating(@PathVariable Long id) {

		MovieEntity movie = restTemplate.getForObject(movieServiceUrl + "/" + id, MovieEntity.class);
		
		Rating rating;
		try {
				rating = restTemplate.getForObject(ratingServiceUrl + "/" + movie.getName(), Rating.class);
		}catch (HttpStatusCodeException ex) {
			
			if(ex.getStatusCode()== HttpStatus.NOT_FOUND) {
				rating = new Rating(null, movie.getName(), 0.0, 0);
			}else {
				rating = new Rating(null, movie.getName(), -1.0, -1);
			}
		}catch (ResourceAccessException ex) {
			log.warn("Exception: ", ex.getMessage());
			rating = new Rating(null, movie.getName(), -1.0, -1);
		}
		
		MovieRating movieRating = new MovieRating();
		movieRating.setMovie(movie);
		movieRating.setRating(rating);
		
		return ResponseEntity.ok(movieRating);

	}
}
