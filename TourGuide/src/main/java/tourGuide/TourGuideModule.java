package tourGuide;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Feign;
import feign.gson.GsonDecoder;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {

	
	@Bean
	public GPSUtilFeignClient getGpsUtil() {
		return Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.GPSUtilFeignClient.class, "http://localhost:8081");
	}
	
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}
	
	@Bean
	public RewardsCentralFeignClient getRewardCentral() {
		return Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.RewardsCentralFeignClient.class, "http://localhost:8082");
	}
	
	@Bean
	public TripPricerFeignClient getTripPricer() {
		return Feign.builder().contract(new SpringMvcContract()).decoder(new GsonDecoder()).target(tourGuide.TripPricerFeignClient.class, "http://localhost:8083");
	}
	
}
