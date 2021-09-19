package com.microservices.movie_catalog_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.movie_catalog_service.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class RatingData {

	@Autowired
	private RestTemplate restTemplate;
	
	
	@HystrixCommand(fallbackMethod="getFallbackUserRatingData", commandProperties ={
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000")
	})
	public UserRating getUserRatingData(String userId, HttpEntity<String> httpEntity){
		
		 ResponseEntity<UserRating> response = restTemplate.exchange("http://ratings-data-service/api/ratingsdata/user/"+userId,HttpMethod.GET, httpEntity,UserRating.class);
		
		 if(response.getStatusCode() == HttpStatus.OK){
			 UserRating userRatings = response.getBody();
			 return userRatings;
	     }
		 else{
			 return null;
		 }
		 
	}
	
	public UserRating getFallbackUserRatingData(String userId, HttpEntity<String> httpEntity){
		 UserRating userRatings = new UserRating();
		 userRatings.setRatings(null);
		 userRatings.setUserId(userId);
		 
		 return userRatings;
	}
}
