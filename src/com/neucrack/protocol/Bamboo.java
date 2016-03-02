package com.neucrack.protocol;

public class Bamboo {
	public final static String Code="206";
	
	public long mTime;//时间
	
	//用户（评论者）信息
	public String mRid;     //评论者rid值
	public String mNickName;//评论者姓名
	
	//房间信息
	public String mQid;
	public String mRoomID;
		
	//评论内容
	public String mContent;
}
