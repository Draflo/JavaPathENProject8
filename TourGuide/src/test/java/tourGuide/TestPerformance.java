package tourGuide;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
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

public class TestPerformance {
	
	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	
	private GPSUtilFeignClient gpsUtilFeignClient;
	
	
	private RewardsService rewardsService;
	
	@Before
	public void setUp() {
		Locale.setDefault(new Locale("en", "US"));	
		
		gpsUtilFeignClient = Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.GPSUtilFeignClient.class, "http://localhost:8081");
		rewardsService = new RewardsService(gpsUtilFeignClient, Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.RewardsCentralFeignClient.class, "http://localhost:8082"));
	}
	
	@Test
	public void highVolumeTrackLocation() throws InterruptedException {
		
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(100000);
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);

		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		
	    StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for(User user : allUsers) {
			tourGuideService.trackUserLocation(user);
		}
		
		ExecutorService tourGuideExecutorService = tourGuideService.getExecutorService();
	    tourGuideExecutorService.shutdown();
	    tourGuideExecutorService.awaitTermination(16, TimeUnit.MINUTES);
	    
	    
		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	
	@Test
	public void highVolumeGetRewards() throws InterruptedException {

		// Users should be incremented up to 100,000, and test finishes within 20 minutes
		InternalTestHelper.setInternalUserNumber(100000);
		
		TourGuideService tourGuideService = new TourGuideService(gpsUtilFeignClient, rewardsService);
		
		tourGuideService.tracker.stopTracking();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		
	    Attraction attraction = gpsUtilFeignClient.getAllAttractions().get(0);
	    Location attractionLocation = new Location(attraction.getLatitude(), attraction.getLongitude());
		List<User> allUsers = new ArrayList<>();
		allUsers = tourGuideService.getAllUsers();
		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attractionLocation, new Date())));
	     
	    allUsers.forEach(u -> rewardsService.calculateRewards(u));
	    
	    ExecutorService executorService = rewardsService.getExecutorService();
	    executorService.shutdown();
	    executorService.awaitTermination(21, TimeUnit.MINUTES);
	    
		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}
		stopWatch.stop();
		

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); 
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}
