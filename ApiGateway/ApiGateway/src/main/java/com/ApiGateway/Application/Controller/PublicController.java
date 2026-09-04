package com.ApiGateway.Application.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ApiGateway.Application.Model.Rating;
import com.ApiGateway.Application.Model.RatingRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/public")
@Slf4j
public class PublicController {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${rating-service.url}")
	private String ratingServiceUrl;
	
	@PostMapping
	public ResponseEntity<Object> addRating(@RequestBody RatingRequest ratingRequest){
		
		Rating rating;
		try {
			rating = restTemplate.postForObject(ratingServiceUrl, ratingRequest, Rating.class);
			return ResponseEntity.ok(rating);
		}
		 catch (HttpStatusCodeException ex) {
				log.error("Error adding movie:{}", ex.getMessage());
				return ResponseEntity.status(ex.getStatusCode()).contentType(MediaType.APPLICATION_JSON)
						.body(ex.getResponseBodyAsString());
			}
	}

}
