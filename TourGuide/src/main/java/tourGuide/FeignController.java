package tourGuide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tourGuide.classes.Attraction;
import tourGuide.classes.VisitedLocation;

@RestController
@RequestMapping
public class FeignController {
	
	@Autowired
	private FeignService feignService;
	
	@GetMapping("/getAllAttractions")
	public List<Attraction> getAllAttractions() {
		return feignService.getAllAttractions();
	}
	
	@GetMapping("/getLocation")
	public VisitedLocation getUserLocation(String userId) {
		return feignService.getUserLocation(userId);
	}

}
