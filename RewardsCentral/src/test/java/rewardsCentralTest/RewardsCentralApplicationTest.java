package rewardsCentralTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import rewardsCentral.RewardsCentralApplication;

@SpringBootTest
public class RewardsCentralApplicationTest {
	
	@Test
	public void contextLoads() {
		
	}

	@Test
	public final void testMain() {
		RewardsCentralApplication.main(new String[] {"arg1", "arg2", "arg3"});
	}

}
