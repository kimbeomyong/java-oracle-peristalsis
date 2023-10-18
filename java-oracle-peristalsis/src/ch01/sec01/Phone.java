package ch01.sec01;

import java.util.Objects;

public class Phone {
	private int phoneId;
	private String phoneName;
	private String phoneMade;
	private String phoneYear;
	private int price;
	
	public Phone(int phoneId, String phoneName, String phoneMade, String phoneYear, int price) {
		super();
		this.phoneId = phoneId;
		this.phoneName = phoneName;
		this.phoneMade = phoneMade;
		this.phoneYear = phoneYear;
		this.price = price;
	}

	public int getPhoneId() {
		return phoneId;
	}

	public String getPhoneName() {
		return phoneName;
	}

	public String getPhoneMade() {
		return phoneMade;
	}

	public String getPhoneYear() {
		return phoneYear;
	}

	public int getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(phoneId);
	}

	@Override
	public boolean equals(Object obj) {
		Phone phone = null;
		if (!(obj instanceof Phone)) {
			return false;
		}
		phone = (Phone)obj;
		return (this.phoneId == phone.phoneId);
	}
	
	@Override
	public String toString() {
		return String.format("%3d \t %10s \t %10s \t %s \t %d", phoneId, phoneName, phoneMade, phoneYear,
				price);
	}

	
	
	
}

