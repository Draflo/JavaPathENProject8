package tourGuide.classes;

public class UserPreferencesDTO {
	
	private String userName;
	private Integer attractionProximity;
	private String currency;
	private Double lowerPricePoInteger;
	private Double highPricePoInteger;
	private Integer tripDuration;
	private Integer ticketQuantity;
	private Integer numberOfAdults;
	private Integer numberOfChildren;
	
	public void setAttractionProximity(Integer attractionProximity) {
		this.attractionProximity = attractionProximity;
	}
	
	public Integer getAttractionProximity() {
		return attractionProximity;
	}
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getLowerPricePoint() {
		return lowerPricePoInteger;
	}

	public void setLowerPricePoint(Double lowerPricePoInteger) {
		this.lowerPricePoInteger = lowerPricePoInteger;
	}

	public Double getHighPricePoint() {
		return highPricePoInteger;
	}

	public void setHighPricePoint(Double highPricePoInteger) {
		this.highPricePoInteger = highPricePoInteger;
	}
	
	public Integer getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(Integer tripDuration) {
		this.tripDuration = tripDuration;
	}

	public Integer getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(Integer ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	
	public Integer getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(Integer numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public Integer getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(Integer numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
