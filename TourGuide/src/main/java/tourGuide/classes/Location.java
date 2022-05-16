package tourGuide.classes;

public class Location {
	
	public Double longitude;
	public Double latitude;

    public Location(Double latitude, Double longitude) {
    	this.latitude = latitude;
    	this.longitude = longitude;
       
    }
    
    public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


}
