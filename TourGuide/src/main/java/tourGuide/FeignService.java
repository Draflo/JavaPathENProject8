package tourGuide;

import java.util.List;

import gpsUtil.location.Attraction;

public interface FeignService {
	
	List<Attraction> getAllAttractions();

}
