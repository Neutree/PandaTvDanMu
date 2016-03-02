package com.neucrack.protocol;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	private String mType;//消息种类
	private long mTime;//时间
	
	//房间信息
	private int mQid;
	private String mRoomID;
	
	//用户（评论者）信息
	private String mIdentity;//评论者身份
	private String mRid;     //评论者rid值
	private String mPlatform;//评论者平台信息
	private String mNickName;//评论者姓名
	private String mUserName;//评论者用户名
	private String mLevel;   //评论者等级
	
	//评论内容
	private String mContent;
	
	//枚举
	//消息类型
	final static String TYPE_DANMU="1";        //弹幕
	final static String TYPE_BAMBOO="206";     //礼物（竹子）
	final static String TYPE_RICE_BALLS="306"; //礼物（饭团）
	final static String TYPE_GIFT_BROADCAST="311";   //其它房间的礼物（佛跳墙）
	final static String TYPE_VISITORS="207";   //房间访客量
	
	//评论者在房间中的身份
	final static String ROLE_MANAGER="60";        //管理员
	final static String ROLE_SUPER_MANAGER="120";  //超管
	final static String ROLE_HOSTER="90";         //主播
	//平台
	final static String PLATFORM_PC_WEB="pc_web";
	final static String PLATFORM_Android="android";
	final static String PLATFORM_Ipad="iPad";
	final static String PLATFORM_Ios="iOS";
	private class Danmu{
		public String Code="1";
	}
	private class Bamboo{
		public String Code="206";
	}
	private class Visitors{
		public String Code="207";
	}
	private class RiceBalls{
		public String Code="306";
	}
	
	
	public Message() {
		
	}
	
	public boolean MessageDecode(String message){
		JSONObject json=null;
		System.out.println(message);
		try{
			json = new JSONObject(message);
		}catch(JSONException e){
			
		}
		if(!JsonDecode(json)){
			return false;
		}
		return true;
	}



	public boolean JsonDecode(JSONObject json){
		try{
			mType = json.getString("type");
			mTime = json.getLong("time");
			mContent = (json.getJSONObject("data")).getString("content");
			if(!mType.equals(TYPE_DANMU))//礼物没有qid
					mQid = 0;
			else
				mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
			mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
			if(mType.equals(TYPE_DANMU))//只有弹幕有身份信息
				mIdentity = json.getJSONObject("data").getJSONObject("from").getString("identity");
			else
				mIdentity = "";
			mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
			if(!mType.equals(TYPE_DANMU)){//观众数量、礼物都没有平台信息
				mPlatform = "";
			}else
				mPlatform = json.getJSONObject("data").getJSONObject("from").getString("__plat");
			if(mType.equals(TYPE_VISITORS)){//房间观众数量没有昵称和用户名以及用户等级
				mNickName = "";
				mUserName = "";
				mLevel = "";
			}
			else{
				mNickName = json.getJSONObject("data").getJSONObject("from").getString("nickName");
				if(mType.equals(TYPE_DANMU)){//礼物没有username
					mUserName = json.getJSONObject("data").getJSONObject("from").getString("userName");
				}else
					mUserName ="";
				mLevel = json.getJSONObject("data").getJSONObject("from").getString("level");
			}
		}catch(JSONException e){
			System.out.println(e);
		}
		return true;
	}



	public String getmType() {
		return mType;
	}



	public void setmType(String mType) {
		this.mType = mType;
	}



	public long getmTime() {
		return mTime;
	}



	public void setmTime(long mTime) {
		this.mTime = mTime;
	}



	public int getmQid() {
		return mQid;
	}



	public void setmQid(int mQid) {
		this.mQid = mQid;
	}



	public String getmRoomID() {
		return mRoomID;
	}



	public void setmRoomID(String mRoomID) {
		this.mRoomID = mRoomID;
	}



	public String getmIdentity() {
		return mIdentity;
	}



	public void setmIdentity(String mIdentity) {
		this.mIdentity = mIdentity;
	}



	public String getmRid() {
		return mRid;
	}



	public void setmRid(String mRid) {
		this.mRid = mRid;
	}



	public String getmPlatform() {
		return mPlatform;
	}



	public void setmPlatform(String mPlatform) {
		this.mPlatform = mPlatform;
	}



	public String getmNickName() {
		return mNickName;
	}



	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}



	public String getmUserName() {
		return mUserName;
	}



	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}



	public String getmLevel() {
		return mLevel;
	}



	public void setmLevel(String mLevel) {
		this.mLevel = mLevel;
	}



	public String getmContent() {
		return mContent;
	}



	public void setmContent(String mContent) {
		this.mContent = mContent;
	}
	
	
	
}
