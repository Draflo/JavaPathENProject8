package gpsUtilTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
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

import gpsUtil.controller.GpsUtilController;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import gpsUtil.service.GpsUtilService;

@RunWith(SpringRunner.class)
@WebMvcTest(GpsUtilController.class)
@ContextConfiguration(classes = GpsUtilController.class)
public class GpsUtilControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private GpsUtilService gpsUtilService;

	@Test
	public final void testGetUserLocation() throws Exception {
		UUID userId = UUID.randomUUID();
		
		when(gpsUtilService.getUserLocation(userId)).thenReturn(new VisitedLocation(userId, new Location(12.0, 12.0), new Date()));
		
		mockMvc.perform(get("/getLocation?userId=" + userId).contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
		
	}

	@Test
	public final void testGetAllAttractions() throws Exception {
		mockMvc.perform(get("/getAllAttractions").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

}
