package tourGuide;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import tourGuide.classes.UserPreferencesDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestTourGuideController {
	
	@Autowired
	private MockMvc mockMvc;	

	@Test
	public final void testIndex() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public final void testGetAllCurrentLocations() throws Exception {
		mockMvc.perform(get("/getAllCurrentLocations")).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].userId", notNullValue()))
		.andExpect(jsonPath("$[0].lastLocation", notNullValue()));
	}


	@Test
	public final void testGetUserPreferences() throws Exception {
		mockMvc.perform(get("/preferences/internalUser0"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userName", is("internalUser0")))
		.andExpect(jsonPath("$.attractionProximity", is(Integer.MAX_VALUE)))
		.andExpect(jsonPath("$.currency", is("USD")))
		.andExpect(jsonPath("$.lowerPricePoint", is(0.0)))
		.andExpect(jsonPath("$.highPricePoint", is(Double.valueOf(Integer.MAX_VALUE))))
		.andExpect(jsonPath("$.tripDuration", is(1)))
		.andExpect(jsonPath("$.ticketQuantity", is(1)))
		.andExpect(jsonPath("$.numberOfAdults", is(1)))
		.andExpect(jsonPath("$.numberOfChildren", is(0)));
	}

	@Test
	public final void testPutUserPreferences() throws Exception {
		UserPreferencesDTO testUserPreferencesDTO = new UserPreferencesDTO();
		
		testUserPreferencesDTO.setUserName("internalUser1");
		testUserPreferencesDTO.setCurrency("EUR");
		testUserPreferencesDTO.setAttractionProximity(20);
		testUserPreferencesDTO.setLowerPricePoint(10.);
		testUserPreferencesDTO.setNumberOfAdults(2);
		testUserPreferencesDTO.setNumberOfChildren(3);
		testUserPreferencesDTO.setTicketQuantity(5);
		testUserPreferencesDTO.setTripDuration(6);
		
		String update = new ObjectMapper().writeValueAsString(testUserPreferencesDTO);
		mockMvc.perform(MockMvcRequestBuilders.put("/preferences")
				.content(update)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userName", is("internalUser1")))
				.andExpect(jsonPath("$.attractionProximity", is(20)))
				.andExpect(jsonPath("$.currency", is("EUR")))
				.andExpect(jsonPath("$.lowerPricePoint", is(10.0)))
				.andExpect(jsonPath("$.highPricePoint", is(Double.valueOf(Integer.MAX_VALUE))))
				.andExpect(jsonPath("$.tripDuration", is(6)))
				.andExpect(jsonPath("$.ticketQuantity", is(5)))
				.andExpect(jsonPath("$.numberOfAdults", is(2)))
				.andExpect(jsonPath("$.numberOfChildren", is(3)));
	}

}
