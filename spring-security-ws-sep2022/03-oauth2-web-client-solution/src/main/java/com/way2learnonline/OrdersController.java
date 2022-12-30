package com.way2learnonline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Controller
public class OrdersController {
	
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private WebClient webClient;
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	@Autowired
	private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
	

	@GetMapping("/orders")
	public String getOrders(Model model,
			@RegisteredOAuth2AuthorizedClient("mywebclient") OAuth2AuthorizedClient authorizedClient) {
		
		
		String jwtAccessToken = authorizedClient.getAccessToken().getTokenValue();
				System.out.println("jwtAccessToken =  " + jwtAccessToken);
				
	    String url = "http://127.0.0.1:8091/orders";	
	    
	    /*
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer " + jwtAccessToken);
	    
	    HttpEntity<String> entity = new HttpEntity<>(headers);
	    ResponseEntity<List<Order>> responseEntity = 
	    		restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Order>>() {});
	    		List<Order> orders = responseEntity.getBody();
	  */

		List<Order> orders = webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<Order>>(){})
				.block();
	    

	    
	    

		model.addAttribute("orders", orders);

		return "orders-page";

	}
}
