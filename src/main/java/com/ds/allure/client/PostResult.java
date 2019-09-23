package com.ds.allure.client;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class PostResult {
	
	static final String URL_CREATE_EMPLOYEE = "https://jsonplaceholder.typicode.com/todos/1";

	public static void main(String[] args) {
		
		  // HttpHeaders
        HttpHeaders headers = new HttpHeaders();
 
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("my_other_key", "my_other_value");


		RestTemplate restTemplate = new RestTemplate();

		// Data attached to the request.
		HttpEntity<String> requestBody = new HttpEntity<>("",headers);

		// Send request with POST method.
		ResponseEntity<String> result = restTemplate.postForEntity(URL_CREATE_EMPLOYEE, requestBody, String.class);

		System.out.println("Status code:" + result.getStatusCode());

		// Code = 200.
		if (result.getStatusCode() == HttpStatus.OK) {
			String resultBody = result.getBody();
			System.out.println("(Client Side) Employee Created: " + resultBody);
		}

	}

}
