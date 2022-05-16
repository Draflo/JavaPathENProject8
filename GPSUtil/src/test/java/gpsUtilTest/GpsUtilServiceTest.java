package gpsUtilTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import gpsUtil.service.GpsUtilService;

@RunWith(MockitoJUnitRunner.class)
public class GpsUtilServiceTest {
	
	@Mock
	private GpsUtil gpsUtil;
	
	@InjectMocks
	private GpsUtilService gpsUtilService;
	
	static List<Attraction> attractions = new ArrayList<>();
	static {
		Locale.setDefault(Locale.US);
		attractions.add(new Attraction("Test", "New York", "US", 48.858482d, 2.294426d));
		attractions.add(new Attraction("Test2","Miami", "US", 46.669752d, 0.368955d));
	}
	

	@Test
	public final void testGetAllAttractions() {
		when(gpsUtil.getAttractions()).thenReturn(attractions);
		List<Attraction> foundList = gpsUtilService.getAllAttractions();
		assertEquals(2, foundList.size());
	}

	@Test
	public final void testGetUserLocation() {
		UUID userId = UUID.randomUUID();
		Location location = new Location(48.858482d, 2.294426d);
		VisitedLocation visitedLocation = new VisitedLocation(userId, location, new Date());
		when(gpsUtil.getUserLocation(userId)).thenReturn(visitedLocation);
		VisitedLocation userLocation = gpsUtilService.getUserLocation(userId);
		
		assertThat(userLocation).isNotNull();
	}

}
