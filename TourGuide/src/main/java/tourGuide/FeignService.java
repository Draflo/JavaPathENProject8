package tourGuide;

import java.util.List;

import tourGuide.classes.Attraction;
import tourGuide.classes.Provider;
import tourGuide.classes.VisitedLocation;

public interface FeignService {
	
	List<Attraction> getAllAttractions();
	
	VisitedLocation getUserLocation(String userId);
	
	Integer getRewards(String attractionId, String userId);
	
	List<Provider> getTripDeals(String apiKey, String userId, 
			int numberOfAdult,  int numberOfChildren, 
			int tripDuration,  int cumulativeRewardPoints);

}
