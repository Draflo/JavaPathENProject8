package rewardsCentralTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import rewardsCentral.Controller.RewardsCentralController;
import rewardsCentral.Service.RewardsCentralService;

@RunWith(SpringRunner.class)
@WebMvcTest(RewardsCentralController.class)
@ContextConfiguration(classes = RewardsCentralController.class)
public class RewardsCentralControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RewardsCentralService rewardsCentralService;

	@Test
	public final void testGetUserAttractionRewardPoints() throws Exception {
		UUID attractionId = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
				
		mockMvc.perform(get("/getRewards?attractionId=" + attractionId + "&userId=" + userId).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

}
