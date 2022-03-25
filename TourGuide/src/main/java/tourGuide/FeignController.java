package tourGuide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.classes.Attraction;
import tourGuide.classes.Provider;
import tourGuide.classes.VisitedLocation;

@RestController
@RequestMapping
public class FeignController {
	
	@Autowired
	private FeignService feignService;
	
	@GetMapping("/getAllAttractions")
	public List<Attraction> getAllAttractions() {
		return feignService.getAllAttractions();
	}
	
	@GetMapping("/getLocation")
	public VisitedLocation getUserLocation(String userId) {
		return feignService.getUserLocation(userId);
	}
	
	@GetMapping("/getRewards")
	public Integer getRewards(@RequestParam(name = "attractionId") String attractionId, @RequestParam(name = "userId") String userId) {
		return feignService.getRewards(attractionId, userId);
	}
	
	@GetMapping("/getTripDeals")
	public List<Provider> getTripDeals(@RequestParam(name = "apiKey") String apiKey, @RequestParam(name = "userId") String userId, 
			@RequestParam(name = "numberOfAdult") int numberOfAdult, @RequestParam(name = "numberOfChildren") int numberOfChildren, 
			@RequestParam(name = "tripDuration") int tripDuration, @RequestParam(name = "cumulativeRewardPoints") int cumulativeRewardPoints) {
		return feignService.getTripDeals(apiKey, userId, numberOfAdult, numberOfChildren, tripDuration, cumulativeRewardPoints);
	}
	


}
