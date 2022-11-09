package com.demo.springDemo;


public class Vehicle {
    String brandName;
    String fuelType;
    int mileage;
	String colour;
   
    public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String toString() {
		return " name : " + brandName
				+ "\n fueltype : " + fuelType
				+ "\n mileage : " + mileage
				+ "\n colour : " + colour;
	}
}
