package com.neucrack.GUI;

import javax.swing.ImageIcon;



public class ListItemDanMu{
	
	private boolean isPhone;
	private boolean isGift;
	private ImageIcon phoneIcon;
	private String userName;
	private String symbolAfterUserName;
	private String message;
	private String giftNumber;
	private String giftUnit;
	private String giftName;
	
	public ListItemDanMu(){
		isPhone=false;
		isGift=false;
		phoneIcon=null;
		userName="";
		symbolAfterUserName="";
		message="";
		giftNumber="";
		giftUnit="";
		giftName="";
	}
	
	public ListItemDanMu(boolean isPhone, boolean isGift, ImageIcon phoneIcon,
			String userName, String symbolAfterUserName, String message,
			String giftNumber, String giftUnit, String giftName) {
		super();
		this.isPhone = isPhone;
		this.isGift = isGift;
		this.phoneIcon = phoneIcon;
		this.userName = userName;
		this.symbolAfterUserName = symbolAfterUserName;
		this.message = message;
		this.giftNumber = giftNumber;
		this.giftUnit = giftUnit;
		this.giftName = giftName;
	}
	public boolean isPhone() {
		return isPhone;
	}
	public void setPhone(boolean isPhone) {
		this.isPhone = isPhone;
	}
	public boolean isGift() {
		return isGift;
	}
	public void setGift(boolean isGift) {
		this.isGift = isGift;
	}
	public ImageIcon getPhoneIcon() {
		return phoneIcon;
	}
	public void setPhoneIcon(ImageIcon phoneIcon) {
		this.phoneIcon = phoneIcon;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSymbolAfterUserName() {
		return symbolAfterUserName;
	}
	public void setSymbolAfterUserName(String symbolAfterUserName) {
		this.symbolAfterUserName = symbolAfterUserName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getGiftNumber() {
		return giftNumber;
	}
	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}
	public String getGiftUnit() {
		return giftUnit;
	}
	public void setGiftUnit(String giftUnit) {
		this.giftUnit = giftUnit;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	

}
