package com.neucrack.protocol;

public class Gift {
	public final static String Code="306";

	public long mTime;//时间
	
	//房间信息
	public int mQid;
	public String mRoomID;
	
	//用户（送礼者）信息
	public String mRid;     //评论者rid值
	public String mNickName;//评论者姓名
	
	//评论内容
	public String mContentId;
	public String mContentName;          //礼物名称
	public String mContentPicChatUrl;    //饭团团
	public String mContentPicEffectUrl;  //饭团动画
	public String mContentPrice;
	public String mContentEffective;
	public String mContentBamboo;
	public String mContentStatus;
	public String mContentCount;
	public String mContentTotal;
	public String mContentCombo;         //连续送的礼物个数
	public String mContentNewBamboo;
}
