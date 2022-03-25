package tourGuide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.classes.VisitedLocation;
import tourGuide.classes.Attraction;
import tourGuide.classes.Provider;

@Service
public class FeignServiceImpl implements FeignService {
	
	@Autowired
	private GPSUtilFeignClient gpsUtilFeignClient;
	
	@Autowired
	private RewardsCentralFeignClient rewardsCentralFeignClient;
	
	@Autowired
	private TripPricerFeignClient tripPricerFeignClient;
	
	@Override
	public List<Attraction> getAllAttractions() {
		return gpsUtilFeignClient.getAllAttractions();
	}
	
	@Override
	public VisitedLocation getUserLocation(String userId) {
		return gpsUtilFeignClient.getUserLocation(userId.toString());
	}

	@Override
	public Integer getRewards(String attractionId, String userId) {
		return rewardsCentralFeignClient.getRewards(attractionId, userId);
	}

	@Override
	public List<Provider> getTripDeals(String apiKey, String userId, 
			 int numberOfAdult,  int numberOfChildren, 
			 int tripDuration,  int cumulativeRewardPoints) {
		return tripPricerFeignClient.getTripDeals(apiKey, userId, numberOfAdult, numberOfChildren, tripDuration, cumulativeRewardPoints);
	}

}
