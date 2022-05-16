package tripPricer.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tripPricer.Provider;
import tripPricer.service.TripPricerService;

@RestController
public class TripPricerController {
	
	@Autowired
	private TripPricerService tripPricerService;
	
	@GetMapping(value = "/getTripDeals")
	public List<Provider> getTripDeals(
			@RequestParam String apiKey, @RequestParam UUID userId, 
			@RequestParam int numberOfAdult, @RequestParam int numberOfChildren, 
			@RequestParam int tripDuration, @RequestParam int cumulativeRewardPoints) {
		return tripPricerService.getTripDeals(apiKey, userId, numberOfAdult, numberOfChildren, tripDuration, cumulativeRewardPoints);
	}

}
