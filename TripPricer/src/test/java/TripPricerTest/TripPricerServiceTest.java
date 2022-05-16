package TripPricerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import tripPricer.Provider;
import tripPricer.TripPricer;
import tripPricer.service.TripPricerService;

@RunWith(MockitoJUnitRunner.class)
public class TripPricerServiceTest {
	
	@Mock
	private TripPricer tripPricer;
	
	@InjectMocks
	private TripPricerService tripPricerService;
	
	static String tripPricerApiKey = "test-server-api-key";
	
	@Test
	public final void testGetTripDeals() {
		UUID userId = UUID.randomUUID();
		List<Provider> providers = new ArrayList<>();
		providers.add(new Provider(UUID.randomUUID(), "Dancing waves", 125));
		providers.add(new Provider(UUID.randomUUID(), "Dream Trips", 521));
		providers.add(new Provider(UUID.randomUUID(), "Holiday Travels", 456));
		providers.add(new Provider(UUID.randomUUID(), "Sunny Days", 410));
		providers.add(new Provider(UUID.randomUUID(), "Enterprize Ventures Limited", 365));
		
		when(tripPricer.getPrice(tripPricerApiKey, userId, 2, 3, 24, 512)).thenReturn(providers);
		
		List<Provider> testProviders = tripPricerService.getTripDeals(tripPricerApiKey, userId, 2, 3, 24, 512);
		
		assertThat(testProviders.size()).isEqualTo(5);
	}

}
