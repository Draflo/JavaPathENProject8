package rewardsCentralTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import rewardCentral.RewardCentral;
import rewardsCentral.Service.RewardsCentralService;

@RunWith(MockitoJUnitRunner.class)
public class RewardsCentralServiceTest {
	
	@Mock
	private RewardCentral rewardCentral;
	
	@InjectMocks
	private RewardsCentralService rewardsCentralService;

	@Test
	public final void testGetAttractionRewardPoints() {
		UUID attractionId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		
		when(rewardCentral.getAttractionRewardPoints(Mockito.any(UUID.class), Mockito.any(UUID.class))).thenReturn(50);	
		
		int rewardsPoint = rewardsCentralService.getAttractionRewardPoints(attractionId, userId);
		
		assertThat(rewardsPoint).isEqualTo(50);
	}

}
