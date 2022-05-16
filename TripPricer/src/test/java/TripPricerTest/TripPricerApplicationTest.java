package TripPricerTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tripPricer.TripPricerApplication;

@SpringBootTest
public class TripPricerApplicationTest {
	
	@Test
	public void contextLoad() {
	}

	@Test
	public final void testMain() {
		TripPricerApplication.main(new String[] {"arg1", "arg2", "arg3"});
	}

}
