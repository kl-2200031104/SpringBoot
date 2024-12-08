package com.real.estate.Bean;

public class PropertyBean extends BaseBean{

	private String Propertytitle;
	private String Propertyowner;
	private String Propertytype;
	private String squrerate;
	private String price;
	private String addresss;
	private String description;
	private String status;
	private String image;
	private String hall;
	private String bedroom;
	private String kitchan;
	private String balcony;
	private String bathroom;
	private long userid;
	private String location;
	
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}
	public String getBedroom() {
		return bedroom;
	}
	public void setBedroom(String bedroom) {
		this.bedroom = bedroom;
	}
	public String getKitchan() {
		return kitchan;
	}
	public void setKitchan(String kitchan) {
		this.kitchan = kitchan;
	}
	public String getBalcony() {
		return balcony;
	}
	public void setBalcony(String balcony) {
		this.balcony = balcony;
	}
	public String getBathroom() {
		return bathroom;
	}
	public void setBathroom(String bathroom) {
		this.bathroom = bathroom;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPropertytitle() {
		return Propertytitle;
	}
	public void setPropertytitle(String propertytitle) {
		Propertytitle = propertytitle;
	}
	public String getPropertyowner() {
		return Propertyowner;
	}
	public void setPropertyowner(String propertyowner) {
		Propertyowner = propertyowner;
	}
	public String getPropertytype() {
		return Propertytype;
	}
	public void setPropertytype(String propertytype) {
		Propertytype = propertytype;
	}
	public String getSqurerate() {
		return squrerate;
	}
	public void setSqurerate(String squrerate) {
		this.squrerate = squrerate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddresss() {
		return addresss;
	}
	public void setAddresss(String addresss) {
		this.addresss = addresss;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String getKey() {
		return id + "";
	}
	@Override
	public String getValue() {
		return location;
	}
	
}
