package tourGuide;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rewardsCentralFeignClient", url = "http://localhost:8082")
public interface RewardsCentralFeignClient {
	
	@GetMapping("/getRewards")
	Integer getRewards(@RequestParam(name = "attractionId") String attractionId, @RequestParam(name = "userId") String userId);

}
