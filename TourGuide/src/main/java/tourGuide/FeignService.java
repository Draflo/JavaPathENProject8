package tourGuide;

import java.util.List;

import tourGuide.classes.VisitedLocation;

public interface FeignService {
	
	List<tourGuide.classes.Attraction> getAllAttractions();
	
	VisitedLocation getUserLocation(String userId);

}
