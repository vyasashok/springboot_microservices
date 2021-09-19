package com.microservice.movieinfoservice.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.movieinfoservice.models.Movie;


@RestController
@RequestMapping("/api/movieinfo")
public class MovieResourceController {
	

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) throws InterruptedException {
    	//Thread.sleep(3000);
    	return new Movie(movieId, "Titanic", "test");
    }

}
