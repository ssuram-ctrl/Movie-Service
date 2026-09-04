package com.microservices.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {
	
	@Autowired
	MovieService service;
	
    @GetMapping("/{id}")
	public ResponseEntity<MovieEntity> getMovie(@PathVariable Long id){
		MovieEntity m = service.movieRead(id);
		log.info("Returned movie with id:{}", id);
		return ResponseEntity.ok(m);
	}
    
    @PostMapping
    public ResponseEntity<MovieEntity> createMovie(@RequestBody MovieEntity movieEntity){
    	MovieEntity mc = service.movieCreate(movieEntity);
    	log.info("Created movie with id:{}", mc.getId());
    	return ResponseEntity.ok(mc);
    	
    }
    
    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id, @RequestBody MovieEntity movieEntity) {
    	service.movieUpdate(id, movieEntity);
    	log.info("Updated movie with id:{}", id);
    }
    
    
    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
    	service.movieDelete(id);
    	log.info("Delete movie with id:{}", id);
    }
    
}
