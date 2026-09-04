package com.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

	@Autowired
	RatingRepository repo;
	
	public Rating updateAverage(String name, double stars) {
		Rating rating = repo.findByName(name);
		if (rating == null) {
			rating = new Rating();
			rating.setName(name);
			rating.setAvgRating(stars);
			rating.setCount(1);

		} else {
			int count = rating.getCount();
			double newAvg = (rating.getAvgRating() * count + stars) / (count + 1);

			rating.setAvgRating(newAvg);
			rating.setCount(++count);
		}
		return repo.save(rating);
	}
	
	
	public Rating fetchRating(String name) {
		Rating rating = repo.findByName(name);
		if(rating == null) {
			throw new com.RatingService.NotFoundException("Movie not found with name: "+ name);
		}
		return rating;
	}
}
