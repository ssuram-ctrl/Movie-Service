package com.ApiGateway.Application.Model;

import lombok.Data;

@Data
public class MovieRating {

	private MovieEntity movie;
	
	private Rating rating;
}
