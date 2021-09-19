package com.microservices.ratingsdataservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.ratingsdataservice.models.Rating;
import com.microservices.ratingsdataservice.models.UserRating;


@RestController
@RequestMapping("/api/ratingsdata")
public class RatingsResourceController {
	
	    @GetMapping("/movies/{movieId}")
	    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
	        return new Rating(movieId, 4);
	    }

	    @GetMapping("/user/{userId}")
	    public UserRating getUserRatings(@PathVariable("userId") String userId) {
	        UserRating userRating = new UserRating();
	        userRating.initData(userId);
	        return userRating;

	    }

}
