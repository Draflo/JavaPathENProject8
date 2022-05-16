package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.GPSUtilFeignClient;
import tourGuide.TripPricerFeignClient;
import tourGuide.classes.Attraction;
import tourGuide.classes.AttractionDTO;
import tourGuide.classes.Location;
import tourGuide.classes.Provider;
import tourGuide.classes.UserLastLocation;
import tourGuide.classes.VisitedLocation;
import tourGuide.helper.InternalTestHelper;
import tourGuide.tracker.Tracker;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GPSUtilFeignClient gpsUtil;
	private final RewardsService rewardsService;
	public final Tracker tracker;
	boolean testMode = true;
	private final ExecutorService executorService = Executors.newFixedThreadPool(200);
	
	@Autowired
	private TripPricerFeignClient tripPricer;
	
	public TourGuideService(GPSUtilFeignClient gpsUtil, RewardsService rewardsService) {
		this.gpsUtil = gpsUtil;
		this.rewardsService = rewardsService;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}
	
	public List<UserReward> getUserRewards(User user) {
		return user.getUserRewards();
	}
	
	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = null;
		try {
			if(user.getVisitedLocations().size() > 0) {
				visitedLocation = user.getLastVisitedLocation();
			} 
			else {
				visitedLocation = trackUserLocation(user).get();	
			}}
		catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return visitedLocation;
	}
	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	
	public void addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricer.getTripDeals(tripPricerApiKey, user.getUserId().toString(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}
	
	public Future<VisitedLocation> trackUserLocation(User user) {
		Future<VisitedLocation> future = executorService.submit(() -> {
		VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId().toString());
		user.addToVisitedLocations(visitedLocation);
		rewardsService.calculateRewards(user);
		return visitedLocation;
		});
		
		return future;
	}

	public List<Attraction> getNearByAttractionsOld(VisitedLocation visitedLocation) {
		List<Attraction> nearbyAttractions = new ArrayList<>();
		for(Attraction attraction : gpsUtil.getAllAttractions()) {
			if(rewardsService.isWithinAttractionProximity(attraction, visitedLocation.location)) {
				nearbyAttractions.add(attraction);
			}
		}
		
		return nearbyAttractions;
	}
	
	public List<Attraction> getFiveNearestAttractions(VisitedLocation userLocation) {
		List<Attraction> attractions = new ArrayList<>();
		SortedMap<Double, Attraction> map = new TreeMap<Double, Attraction>();
		SortedMap<Double, Attraction> fiveAttractions = new TreeMap<Double, Attraction>();
			for(Attraction attraction : gpsUtil.getAllAttractions()) {
				Double distance = rewardsService.getDistance(userLocation.location, new Location(attraction.latitude, attraction.longitude));
				map.put(distance, attraction);
			}
			Iterator<Double> iterator = map.keySet().iterator();
			int i = 0;
			while (i < 5 && iterator.hasNext()) {
				Double distance = iterator.next();
				Attraction attraction = map.get(distance);
				fiveAttractions.put(distance, attraction);
				i++;
			}
			fiveAttractions.forEach((distance, attraction) -> {
				attractions.add(attraction);
			});
			
		return attractions;
		
	}
	
	public List<AttractionDTO> getNearbyAttractions(User user) {
		List<Attraction> fiveAttractions = getFiveNearestAttractions(user.getLastVisitedLocation());
		List<AttractionDTO> attractionList = new ArrayList<AttractionDTO>();
		fiveAttractions.forEach((attraction) -> {
			AttractionDTO attractionDto = new AttractionDTO();
			
			attractionDto.setName(attraction.attractionName);
			attractionDto.setLocation(new Location(attraction.latitude, attraction.longitude));
			attractionDto.setUserLocation(user.getLastVisitedLocation().location);
			attractionDto.setDistance(rewardsService.getDistance(user.getLastVisitedLocation().location, new Location(attraction.latitude, attraction.longitude)));
			attractionDto.setRewardPoint(rewardsService.getRewardPoints(attraction, user));
		});
		return attractionList;
	}
	
	public List<UserLastLocation> getAllUserLastLocation() {
		List<UserLastLocation> listLastLocations = new ArrayList<>();
		for(User user : getAllUsers()) {
			UserLastLocation lastLocation = new UserLastLocation();
			lastLocation.setUserId(user.getUserId().toString());
			lastLocation.setLastLocation(user.getLastVisitedLocation().location);
			listLastLocations.add(lastLocation);
		}
		
		return listLastLocations;
	}
	
	public void bulkCalculateReward(List<User> users) {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		users.forEach(user -> 
		executorService.submit(new Thread(() -> rewardsService.calculateRewards(user))));
		
		executorService.shutdown();
		try {
			executorService.awaitTermination(20, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);
			
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}
	
}
