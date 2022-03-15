package tourGuide;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.classes.VisitedLocation;

@FeignClient(name = "postFeignClient", url = "http://localhost:8081")
public interface PostFeignClient {
	
	@GetMapping("/getAllAttractions")
	List<tourGuide.classes.Attraction> getAllAttractions();
	
	@GetMapping("/getLocation")
	VisitedLocation getUserLocation(@RequestParam(name = "userId") String userId);

}
