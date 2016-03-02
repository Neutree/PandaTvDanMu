package com.neucrack.protocol;

public class Danmu {
	public final static String Code="1";
	
	public long mTime;//时间
	
	//房间信息
	public int mQid;
	public String mRoomID;
	
	//用户（评论者）信息
	public String mIdentity;//评论者身份
	public String mRid;     //评论者rid值
	public String mPlatform;//评论者平台信息
	public String mNickName;//评论者姓名
	public String mUserName;//评论者用户名
	public String mLevel;   //评论者等级
	
	//评论内容
	public String mContent;
}
