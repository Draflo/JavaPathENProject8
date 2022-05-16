package gpsUtil.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@Service
public class GpsUtilService {
	
	@Autowired
	private GpsUtil gpsUtil;
	
	private GpsUtilService() {
		
	}
	
	public List<Attraction> getAllAttractions() {
		return gpsUtil.getAttractions();
	}
	
	public VisitedLocation getUserLocation(UUID userId) {
		return gpsUtil.getUserLocation(userId);
	}

}
