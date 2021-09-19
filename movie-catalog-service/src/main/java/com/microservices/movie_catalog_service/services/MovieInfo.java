package com.microservices.movie_catalog_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.microservices.movie_catalog_service.models.CatalogItem;
import com.microservices.movie_catalog_service.models.Movie;
import com.microservices.movie_catalog_service.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackMovieInfo", commandProperties ={
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
	})
	public List<CatalogItem> getMovieInfo(UserRating userRatings, HttpEntity<String> httpEntity){

		 return userRatings.getRatings().stream().map(rating ->{
			 
			 ResponseEntity<Movie> response = restTemplate.exchange("http://movie-info-service/api/movieinfo/"+rating.getMovieId(),HttpMethod.GET,httpEntity, Movie.class);
			 
			 Movie movie = new Movie();
			 
			 if(response.getStatusCode() == HttpStatus.OK){
				 movie = response.getBody();
				 return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
			 }
			 else{
				 return null;
			 }
		 }).collect(Collectors.toList());
	}
	
	public List<CatalogItem> getFallbackMovieInfo(UserRating userRatings,HttpEntity<String> httpEntity){
		 List<CatalogItem> catalogItemList = new ArrayList<CatalogItem>();
		 
		 catalogItemList.add(new CatalogItem("No movie", "movie not available", 0));
		 
		 return catalogItemList;
	}

}
