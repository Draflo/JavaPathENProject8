package tourGuide;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.classes.Provider;

@FeignClient(name = "tripPricerFeignClient", url = "http://localhost:8083")
public interface TripPricerFeignClient {
	
	@GetMapping("/getTripDeals")
	List<Provider> getTripDeals(@RequestParam(name = "apiKey") String apiKey, @RequestParam(name = "userId") String userId, 
			@RequestParam(name = "numberOfAdult") int numberOfAdult, @RequestParam(name = "numberOfChildren") int numberOfChildren, 
			@RequestParam(name = "tripDuration") int tripDuration, @RequestParam(name = "cumulativeRewardPoints") int cumulativeRewardPoints);

}
