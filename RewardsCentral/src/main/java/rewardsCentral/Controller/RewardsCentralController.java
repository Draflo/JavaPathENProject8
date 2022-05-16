package rewardsCentral.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rewardsCentral.Service.RewardsCentralService;

@RestController
public class RewardsCentralController {
	
	@Autowired
	private RewardsCentralService rewardsCentralService;
	
	@GetMapping("/getRewards")
	public int getUserAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId) {
		return rewardsCentralService.getAttractionRewardPoints(attractionId, userId);
	}

}
