package tourGuide.user;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import org.javamoney.moneta.Money;

import tourGuide.classes.UserPreferencesDTO;


public class UserPreferences {
	
	private int attractionProximity = Integer.MAX_VALUE;
	private CurrencyUnit currency = Monetary.getCurrency("USD");
	private Money lowerPricePoint = Money.of(0, currency);
	private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);
	private int tripDuration = 1;
	private int ticketQuantity = 1;
	private int numberOfAdults = 1;
	private int numberOfChildren = 0;
	
	public UserPreferences() {
	}
	
	public void setAttractionProximity(int attractionProximity) {
		this.attractionProximity = attractionProximity;
	}
	
	public int getAttractionProximity() {
		return attractionProximity;
	}
	
	public Money getLowerPricePoint() {
		return lowerPricePoint;
	}

	public void setLowerPricePoint(Money lowerPricePoint) {
		this.lowerPricePoint = lowerPricePoint;
	}

	public Money getHighPricePoint() {
		return highPricePoint;
	}

	public void setHighPricePoint(Money highPricePoint) {
		this.highPricePoint = highPricePoint;
	}
	
	public int getTripDuration() {
		return tripDuration;
	}

	public void setTripDuration(int tripDuration) {
		this.tripDuration = tripDuration;
	}

	public int getTicketQuantity() {
		return ticketQuantity;
	}

	public void setTicketQuantity(int ticketQuantity) {
		this.ticketQuantity = ticketQuantity;
	}
	
	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	
	public CurrencyUnit getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyUnit currency) {
		this.currency = currency;
	}
	
	
	public void mapTo(UserPreferencesDTO userPreferencesDTO, String userName) {
		userPreferencesDTO.setUserName(userName);
		userPreferencesDTO.setCurrency(currency.getCurrencyCode());
		userPreferencesDTO.setAttractionProximity(attractionProximity);
		userPreferencesDTO.setHighPricePoint(highPricePoint.getNumber().doubleValue());
		userPreferencesDTO.setLowerPricePoint(lowerPricePoint.getNumber().doubleValue());
		userPreferencesDTO.setNumberOfAdults(numberOfAdults);
		userPreferencesDTO.setNumberOfChildren(numberOfChildren);
		userPreferencesDTO.setTicketQuantity(ticketQuantity);
		userPreferencesDTO.setTripDuration(tripDuration);
		
	}
	
	public void mapFrom(UserPreferencesDTO dto)
	{
		if(dto.getCurrency() != null) {
			setCurrency(Monetary.getCurrency(dto.getCurrency()));
		}
		
		if(dto.getAttractionProximity() != null) {
			setAttractionProximity(dto.getAttractionProximity());
		}
		
		if(dto.getHighPricePoint() != null) {
			setHighPricePoint(Money.of(dto.getHighPricePoint(), currency));
		}
		
		if(dto.getLowerPricePoint() != null) {
			setLowerPricePoint(Money.of(dto.getLowerPricePoint(), currency));
		}
		
		if(dto.getNumberOfAdults() != null) {
			setNumberOfAdults(dto.getNumberOfAdults());
		}
		
		if(dto.getNumberOfChildren() != null) {
			setNumberOfChildren(dto.getNumberOfChildren());
		}
		
		if(dto.getTicketQuantity() != null) {
			setTicketQuantity(dto.getTicketQuantity());
		}
		
		if(dto.getTripDuration() != null) {
			setTripDuration(dto.getTripDuration());
		}
	}

}
