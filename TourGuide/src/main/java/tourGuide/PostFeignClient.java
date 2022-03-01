package tourGuide;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import gpsUtil.location.Attraction;

@org.springframework.cloud.openfeign.FeignClient(name = "postFeignClient", url = "${http://localhost:8081}")
public interface PostFeignClient {
	
	@GetMapping("/getAllAttractions")
	List<Attraction> getAllAttractions();

}
