package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.classes.Attraction;
import tourGuide.classes.Location;
import tourGuide.classes.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

public class TestRewardsService {
	
	private GPSUtilFeignClient gpsUtilFeignClient;
	private RewardsService rewardsService;
	
	@Before
	public void setUp() {
		Locale.setDefault(new Locale("en", "US"));
		
		gpsUtilFeignClient = Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.GPSUtilFeignClient.class, "http://localhost:8081");
		rewardsService = new RewardsService(gpsUtilFeignClient, Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.RewardsCentralFeignClient.class, "http://localhost:8082"));
	}

	@Test
	public void userGetRewards() throws InterruptedException {
		
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction = gpsUtilFeignClient.getAllAttractions().get(0);
	    Location attractionLocation = new Location(attraction.latitude, attraction.longitude);
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attractionLocation, new Date()));
		tourGuideService.trackUserLocation(user);
		
		ExecutorService tourGuideExecutorService = tourGuideService.getExecutorService();
		tourGuideExecutorService.shutdown();
		tourGuideExecutorService.awaitTermination(10, TimeUnit.SECONDS);
		
		
		ExecutorService executorService = rewardsService.getExecutorService();
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
		
		List<UserReward> userRewards = user.getUserRewards();
		tourGuideService.tracker.stopTracking();
		assertTrue(userRewards.size() == 1);
	}
	
	@Test
	public void isWithinAttractionProximity() {
		Attraction attraction = gpsUtilFeignClient.getAllAttractions().get(0);
	    Location attractionLocation = new Location(attraction.latitude, attraction.longitude);
		assertTrue(rewardsService.isWithinAttractionProximity(attraction, attractionLocation));
	}
	
	
	@Test
	public void nearAllAttractions() throws InterruptedException {
		rewardsService.setProximityBuffer(Integer.MAX_VALUE);

		InternalTestHelper.setInternalUserNumber(1);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
		
		ExecutorService executorService = rewardsService.getExecutorService();
		executorService.shutdown();
		executorService.awaitTermination(10, TimeUnit.SECONDS);
		
		List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
		tourGuideService.tracker.stopTracking();

		assertEquals(gpsUtilFeignClient.getAllAttractions().size(), userRewards.size());
	}
	
}
