package com.tutego.date4u.dto;

public class SearchDTO {

	private int minAge;
	private int maxAge;
	private short minHorn;
	private short maxHorn;
	private byte gender;

	public SearchDTO( int minAge, int maxAge, short minHorn, short maxHorn, byte gender ) {
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.minHorn = minHorn;
		this.maxHorn = maxHorn;
		this.gender = gender;
	}

	public SearchDTO() {
		setMinAge( 18 );
		setMaxAge( 50 );
		setMinHorn( ( short ) 0 );
		setMaxHorn( ( short ) 20 );
		setGender( ( byte ) 0 );
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge( int minAge ) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge( int maxAge ) {
		this.maxAge = maxAge;
	}

	public short getMinHorn() {
		return minHorn;
	}

	public void setMinHorn( short minHorn ) {
		this.minHorn = minHorn;
	}

	public short getMaxHorn() {
		return maxHorn;
	}

	public void setMaxHorn( short maxHorn ) {
		this.maxHorn = maxHorn;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender( byte gender ) {
		this.gender = gender;
	}
}
