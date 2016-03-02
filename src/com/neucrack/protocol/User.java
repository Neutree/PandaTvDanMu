package com.neucrack.protocol;

public class User {
	
	//身份
	public final static String ROLE_USER="30";           //普通用户（已经登录）
	public final static String ROLE_MANAGER="60";        //管理员
	public 	final static String ROLE_HOSTER="90";         //主播
	public final static String ROLE_SUPER_MANAGER="120";  //超管
	
	
	
	private String mUserName;
	private String mNickName;
	private String mLevel;
	private String mBamboo;
	private String mRole;
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmNickName() {
		return mNickName;
	}
	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}
	public String getmLevel() {
		return mLevel;
	}
	public void setmLevel(String mLevel) {
		this.mLevel = mLevel;
	}
	public String getmBamboo() {
		return mBamboo;
	}
	public void setmBamboo(String mBamboo) {
		this.mBamboo = mBamboo;
	}
	public String getmRole() {
		return mRole;
	}
	public void setmRole(String mRole) {
		this.mRole = mRole;
	}
	
}
