package tourGuide;

import java.util.List;

import org.springframework.stereotype.Service;

import gpsUtil.location.Attraction;

@Service
public class FeignServiceImpl implements FeignService {
	
	
	private PostFeignClient feignClient;
	
	@Override
	public List<Attraction> getAllAttractions() {
		return feignClient.getAllAttractions();
	}

}
