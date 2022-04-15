package tourGuide;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.classes.Attraction;
import tourGuide.classes.UserLastLocation;
import tourGuide.classes.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

public class TestTourGuideService {
	
	private GPSUtilFeignClient gpsUtilFeignClient;
	private RewardsCentralFeignClient rewardsCentralFeignClient;
	
	@Before
	public void setUp() {
		Locale.setDefault(new Locale("en", "US"));
		
		gpsUtilFeignClient = Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.GPSUtilFeignClient.class, "http://localhost:8081");
		rewardsCentralFeignClient = Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.RewardsCentralFeignClient.class, "http://localhost:8082");
	}

	@Test
	public void getUserLocation() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		tourGuideService.tracker.stopTracking();
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}
	
	@Test
	public void addUser() {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		User retrivedUser = tourGuideService.getUser(user.getUserName());
		User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

		tourGuideService.tracker.stopTracking();
		
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}
	
	@Test
	public void getAllUsers() {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		tourGuideService.addUser(user);
		tourGuideService.addUser(user2);
		
		List<User> allUsers = tourGuideService.getAllUsers();

		tourGuideService.tracker.stopTracking();
		
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}
	
	@Test
	public void trackUser() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(user.getUserId(), visitedLocation.userId);
	}
	
	
	@Test
	public void getNearbyAttractions() throws InterruptedException, ExecutionException {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		VisitedLocation visitedLocation = tourGuideService.trackUserLocation(user).get();
		
		List<Attraction> attractions = tourGuideService.getFiveNearestAttractions(visitedLocation);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(5, attractions.size());
	}
	
	@Test
	public void getAllCurrentLocations() {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(10);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		tourGuideService.tracker.stopTracking();
		
		List<UserLastLocation> lastLocations = tourGuideService.getAllUserLastLocation();
		
		assertEquals(10, lastLocations.size());
		lastLocations.forEach((userLastLocation) -> assertTrue(userLastLocation.getLastLocation() != null));
		
		
	}
	
	public void getTripDeals() {
		RewardsService rewardsService = new RewardsService(gpsUtilFeignClient, rewardsCentralFeignClient);
		InternalTestHelper.setInternalUserNumber(0);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		List<Provider> providers = tourGuideService.getTripDeals(user);
		
		tourGuideService.tracker.stopTracking();
		
		assertEquals(10, providers.size());
	}
	
	
}
