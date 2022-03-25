package tourGuide.classes;

import java.util.UUID;

public class Provider {
	
	private String name;
	private Double price;
	private UUID tripId;
	
	public Provider() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}
	
	

}
