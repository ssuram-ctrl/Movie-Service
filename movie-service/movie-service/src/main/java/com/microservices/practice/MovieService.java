package com.microservices.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ExceptionHandler.InvalidExceptions;
import ExceptionHandler.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MovieService {

	@Autowired MovieRepository movieRepo;
	
	public  MovieEntity movieCreate(MovieEntity movieEntity) {
		if(movieEntity == null) {
			throw new InvalidExceptions("Invalid movie");
		}
		return movieRepo.save(movieEntity);
		
	}
	
	public MovieEntity movieRead(Long id) {
		return movieRepo.findById(id).orElseThrow(() -> new InvalidExceptions("Invalid Id"));
		
	}

	public void movieUpdate(Long id, MovieEntity movieUpdate) {

	    if (movieUpdate == null || id == null) {
	        throw new InvalidExceptions("Invalid Movie");
	    }

	    MovieEntity finalUpdate = movieRepo.findById(id)
	            .orElseThrow(() -> new NotFoundException("Movie not found"));

	    finalUpdate.setName(movieUpdate.getName());
	    finalUpdate.setDirector(movieUpdate.getDirector());
	    finalUpdate.setActors(movieUpdate.getActors());

	    movieRepo.save(finalUpdate);

	    log.info("Movie updated successfully with id: {}", id);
	}
	
	public void movieDelete(Long id) {
		if(movieRepo.existsById(id)) {
			movieRepo.deleteById(id);
		}
		else {
			throw new NotFoundException("Movie not found");
		}
	}
}
