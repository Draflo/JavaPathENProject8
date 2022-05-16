package gpsUtil.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import gpsUtil.service.GpsUtilService;

@RestController
public class GpsUtilController {
	
	@Autowired
	private GpsUtilService gpsUtilService;
	
	@GetMapping(value = "/getLocation")
	public VisitedLocation getUserLocation(@RequestParam UUID userId) {
		VisitedLocation visitedLocation = gpsUtilService.getUserLocation(userId);
		return visitedLocation;
	}
	
	@GetMapping(value = "/getAllAttractions")
	public List<Attraction> getAllAttractions() {
		return gpsUtilService.getAllAttractions();
	}

}
