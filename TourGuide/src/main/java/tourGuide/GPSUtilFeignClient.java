package tourGuide;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tourGuide.classes.VisitedLocation;
import tourGuide.classes.Attraction;

@FeignClient(name = "gPSUtilFeignClient", url = "http://localhost:8081")
public interface GPSUtilFeignClient {
	
	@GetMapping("/getAllAttractions")
	List<Attraction> getAllAttractions();
	
	@GetMapping("/getLocation")
	VisitedLocation getUserLocation(@RequestParam(name = "userId") String userId);

}
