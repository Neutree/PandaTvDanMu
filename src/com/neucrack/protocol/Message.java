package com.neucrack.protocol;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {

	public Object MessageDecode(String message){
		JSONObject json=null;
//		System.out.println(message);
		try{
			json = new JSONObject(message);
		}catch(JSONException e){
			System.out.println("信息不完整\n"+message);
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
				//danmu.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
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
				//部分弹幕服务器没有这个。。。所以屏蔽掉
				//bamboo.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				bamboo.mContent = json.getJSONObject("data").getString("content");
				message = bamboo;
			}
			else if(type.equals(Visitors.Code)){
				Visitors visitor=new Visitors();
				visitor.mTime = time;
				visitor.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				//部分弹幕服务器没有这个。。。所以屏蔽掉
				//visitor.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				visitor.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				visitor.mContent = json.getJSONObject("data").getString("content");
				message = visitor;
			}
			else if(type.equals(Gift.Code)){
				Gift gift=new Gift();
				gift.mTime = time;
				gift.mRid = json.getJSONObject("data").getJSONObject("from").getString("rid");
				gift.mNickName = json.getJSONObject("data").getJSONObject("from").getString("nickName");
				//部分弹幕服务器没有这个。。。所以屏蔽掉
				//gift.mQid = json.getJSONObject("data").getJSONObject("to").getInt("toqid");
				gift.mRoomID = json.getJSONObject("data").getJSONObject("to").getString("toroom");
				gift.mContentId = json.getJSONObject("data").getJSONObject("content").getString("id");
				gift.mContentName = json.getJSONObject("data").getJSONObject("content").getString("name");
				gift.mContentPicChatUrl = json.getJSONObject("data").getJSONObject("content").getJSONObject("pic").getJSONObject("pc").getString("chat");
				gift.mContentPicEffectUrl = json.getJSONObject("data").getJSONObject("content").getJSONObject("pic").getJSONObject("pc").getString("effect");
				gift.mContentPrice = json.getJSONObject("data").getJSONObject("content").getString("price");
				gift.mContentEffective = json.getJSONObject("data").getJSONObject("content").getString("effective");
				gift.mContentBamboo = json.getJSONObject("data").getJSONObject("content").getString("bamboo");
				gift.mContentStatus = json.getJSONObject("data").getJSONObject("content").getString("status");
				gift.mContentCount = json.getJSONObject("data").getJSONObject("content").getString("count");
				gift.mContentTotal = json.getJSONObject("data").getJSONObject("content").getString("total");
				gift.mContentCombo = json.getJSONObject("data").getJSONObject("content").getString("combo");
				gift.mContentNewBamboos = json.getJSONObject("data").getJSONObject("content").getString("newBamboos");
				message = gift;
			}
			else if(type.equals(GiftOtherRoom.Code)){//其它房间的大礼物
				System.out.println("其它房间送的大礼物："+json.toString());
			}
			else{
				System.out.println("未知类型数据 Code:"+type+"\n内容："+json.toString());
			}
		}catch(JSONException e){
			System.out.println(e);
		}
		return message;
	}	
}
