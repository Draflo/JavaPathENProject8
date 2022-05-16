package TripPricerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
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

import tripPricer.Provider;
import tripPricer.controller.TripPricerController;
import tripPricer.service.TripPricerService;

@RunWith(SpringRunner.class)
@WebMvcTest(TripPricerController.class)
@ContextConfiguration(classes = TripPricerController.class)
public class TripPricerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TripPricerService tripPricerService;

	@Test
	public final void testGetTripDeals() throws Exception {
		UUID userId = UUID.randomUUID();
		List<Provider> providers = new ArrayList<>();
		providers.add(new Provider(UUID.randomUUID(), "Dancing waves", 125));
		providers.add(new Provider(UUID.randomUUID(), "Dream Trips", 521));
		
		when(tripPricerService.getTripDeals("test-server-api-key", userId, 2, 3, 24, 512)).thenReturn(providers);
		
		mockMvc.perform(get("/getTripDeals?apiKey=test-server-api-key&userId=" + userId + "&numberOfAdult=2&numberOfChildren=3&tripDuration=24&cumulativeRewardPoints=500").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

}
