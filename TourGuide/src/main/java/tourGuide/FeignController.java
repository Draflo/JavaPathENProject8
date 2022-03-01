package tourGuide;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;

@RestController
@RequestMapping
public class FeignController {
	
	@Autowired
	private FeignService feignService;
	
	@GetMapping("/getAllAttractions")
	public List<Attraction> getAllAttractions() {
		return feignService.getAllAttractions();
	}

}
