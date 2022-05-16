package rewardsCentral.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rewardCentral.RewardCentral;

@Service
public class RewardsCentralService {
	
	@Autowired
	private RewardCentral rewardCentral;
	
	public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
		return rewardCentral.getAttractionRewardPoints(attractionId, userId);
	}

}
