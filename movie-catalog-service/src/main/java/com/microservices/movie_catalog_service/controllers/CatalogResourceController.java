package com.microservices.movie_catalog_service.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.movie_catalog_service.models.CatalogItem;
import com.microservices.movie_catalog_service.models.Movie;
import com.microservices.movie_catalog_service.models.Rating;
import com.microservices.movie_catalog_service.models.UserRating;
import com.microservices.movie_catalog_service.services.MovieInfo;
import com.microservices.movie_catalog_service.services.RatingData;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api/moviecatalog")
public class CatalogResourceController {
 
	 @Autowired
	 private MovieInfo movieService;
	 
	 @Autowired
	 private RatingData ratingService;
	 

	@Autowired
	private RestTemplate restTemplate;
	 
	 @GetMapping("/{userId}")
	 public List<CatalogItem> getCatalog(@PathVariable("userId") String userId,HttpServletRequest request) {
		 
		 HttpEntity<String> httpEntity= this.generateHeader(request);
		 
		// ResponseEntity<UserRating> response = restTemplate.exchange("http://ratings-data-service/api/ratingsdata/user/"+userId,HttpMethod.GET, httpEntity,UserRating.class);
		 
		 UserRating userRatings = ratingService.getUserRatingData(userId, httpEntity);
		 
		 return movieService.getMovieInfo(userRatings, httpEntity);
		 	 
	 }
	 
	 
	 private HttpEntity<String> generateHeader(HttpServletRequest request){
		 String token = request.getHeader("Authorization").split("Bearer")[1];
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setBearerAuth(token);
		 HttpEntity<String> req = new HttpEntity<String>("Headers",headers);
		 return req;
	 }

}
