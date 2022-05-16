package tripPricer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tripPricer.Provider;
import tripPricer.TripPricer;

@Service
public class TripPricerService {
	
	@Autowired
	private TripPricer tripPricer;
	
	public List<Provider> getTripDeals(String apiKey, UUID userId, int numberOfAdult, int numberOfChildren, int tripDuration, int cumulativeRewardPoints) {
		return tripPricer.getPrice(apiKey, userId, numberOfAdult, numberOfChildren, tripDuration, cumulativeRewardPoints);
	}

}
