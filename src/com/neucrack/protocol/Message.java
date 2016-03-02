package com.neucrack.protocol;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
//	//枚举
//	//消息类型
//	final static String TYPE_DANMU="1";        //弹幕
//	final static String TYPE_BAMBOO="206";     //礼物（竹子）
//	final static String TYPE_RICE_BALLS="306"; //礼物（饭团）
//	final static String TYPE_GIFT_BROADCAST="311";   //其它房间的礼物（佛跳墙）
//	final static String TYPE_VISITORS="207";   //房间访客量

	public Object MessageDecode(String message){
		JSONObject json=null;
		System.out.println(message);
		try{
			json = new JSONObject(message);
		}catch(JSONException e){
			
		}
		return JsonDecode(json);
	}

	public Object JsonDecode(JSONObject json){
		Object message=null;
		String type="";
		long time;
		try{
			type = json.getString("type");
			time = json.getLong("time");
			
			if(type.equals(Danmu.Code)){
				Danmu danmu=new Danmu();
				danmu.mTime = time;
				danmu.mIdentity = json.getJSONObject("data").getJSONObject("from").getString("identity");
				danmu.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				danmu.mPlatform = json.getJSONObject("data").getJSONObject("from").getString("__plat");
				danmu.mNickName = json.getJSONObject("data").getJSONObject("from").getString("nickName");
				danmu.mLevel = json.getJSONObject("data").getJSONObject("from").getString("level");
				danmu.mUserName = json.getJSONObject("data").getJSONObject("from").getString("userName");
				danmu.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				danmu.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				danmu.mContent = json.getJSONObject("data").getString("content");
				message = danmu;
			}
			else if(type.equals(Bamboo.Code)){
				Bamboo bamboo=new Bamboo();
				bamboo.mTime = time;
				bamboo.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				bamboo.mNickName = json.getJSONObject("data").getJSONObject("from").getString("nickName");
				bamboo.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				bamboo.mQid = json.getJSONObject("data").getJSONObject("to").getString("toqid");
				bamboo.mContent = json.getJSONObject("data").getString("content");
				message = bamboo;
			}
			else if(type.equals(Visitors.Code)){
				Visitors visitor=new Visitors();
				visitor.mTime = time;
				visitor.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				visitor.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				visitor.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				visitor.mContent = json.getJSONObject("data").getString("content");
				message = visitor;
			}
			else if(type.equals(Gift.Code)){
				Gift gift=new Gift();
				gift.mTime = time;
				gift.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				gift.mNickName = json.getJSONObject("data").getJSONObject("from").getString("nickName");
				gift.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				gift.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				gift.mContentId = json.getJSONObject("data").getJSONObject("contemt").getString("id");
				gift.mContentName = json.getJSONObject("data").getJSONObject("contemt").getString("name");
				gift.mContentPicChatUrl = json.getJSONObject("data").getJSONObject("contemt").getJSONObject("pic").getJSONObject("pc").getString("chat");
				gift.mContentPicEffectUrl = json.getJSONObject("data").getJSONObject("contemt").getJSONObject("pic").getJSONObject("pc").getString("effect");
				gift.mContentPrice = json.getJSONObject("data").getJSONObject("contemt").getString("price");
				gift.mContentEffective = json.getJSONObject("data").getJSONObject("contemt").getString("effective");
				gift.mContentBamboo = json.getJSONObject("data").getJSONObject("contemt").getString("bamboo");
				gift.mContentStatus = json.getJSONObject("data").getJSONObject("contemt").getString("status");
				gift.mContentCount = json.getJSONObject("data").getJSONObject("contemt").getString("count");
				gift.mContentTotal = json.getJSONObject("data").getJSONObject("contemt").getString("total");
				gift.mContentCombo = json.getJSONObject("data").getJSONObject("contemt").getString("combo");
				gift.mContentBamboo = json.getJSONObject("data").getJSONObject("contemt").getString("newBamboos");
				message = gift;
			}
			else{
				System.out.println("未知类型数据");
			}
		}catch(JSONException e){
			System.out.println(e);
		}
		return message;
	}	
}
