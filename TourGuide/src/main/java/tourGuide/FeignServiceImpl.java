package tourGuide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tourGuide.classes.VisitedLocation;

@Service
public class FeignServiceImpl implements FeignService {
	
	@Autowired
	private PostFeignClient feignClient;
	
	@Override
	public List<tourGuide.classes.Attraction> getAllAttractions() {
		return feignClient.getAllAttractions();
	}
	
	@Override
	public VisitedLocation getUserLocation(String userId) {
		return feignClient.getUserLocation(userId.toString());
	}

}
