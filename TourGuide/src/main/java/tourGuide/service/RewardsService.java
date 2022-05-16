package tourGuide.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import tourGuide.GPSUtilFeignClient;
import tourGuide.RewardsCentralFeignClient;
import tourGuide.classes.Attraction;
import tourGuide.classes.Location;
import tourGuide.classes.VisitedLocation;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GPSUtilFeignClient gpsUtil;
	private final RewardsCentralFeignClient rewardsCentral;
	private final ExecutorService executorService = Executors.newFixedThreadPool(100);
	
	public RewardsService(GPSUtilFeignClient gpsUtil, RewardsCentralFeignClient rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = gpsUtil.getAllAttractions();
		
		
		for(VisitedLocation visitedLocation : userLocations) {
			for(Attraction attraction : attractions) {
				if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
					if(nearAttraction(visitedLocation, attraction)) {
						
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						
					}
				}
			}
		}
		
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		Location attractionLocation = new Location(attraction.getLatitude(), attraction.getLongitude());
		return getDistance(attractionLocation, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		Location attractionLocation = new Location(attraction.getLatitude(), attraction.getLongitude());
		return getDistance(attractionLocation, visitedLocation.location) > proximityBuffer ? false : true;
	}
	
	int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getRewards(attraction.attractionId.toString(), user.getUserId().toString());
	}
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

}
