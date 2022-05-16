package gpsUtilTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import gpsUtil.GpsUtilApplication;

@SpringBootTest
public class GpsUtilApplicationTest {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void mainTest() {
		GpsUtilApplication.main(new String[] {"arg1", "arg2", "arg3"});
	}

}
