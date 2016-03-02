package com.neucrack.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.neucrack.protocol.ConnectDanMuServer;
import com.neucrack.server.HttpRequest;

public class Test {

	@org.junit.Test
	public void test() throws UnknownHostException, IOException {
//		ConnectDanMuServer danMuConnection = new ConnectDanMuServer();
//		if(danMuConnection.ConnectToDanMuServer("313180"))
//			System.out.println("连接弹幕服务器成功");
//		else
//			System.out.println("连接弹幕服务器失败");
//		
/*		Socket socket = null;
		socket=new Socket("127.0.0.1",8000);
		PrintWriter os=new PrintWriter(socket.getOutputStream());
		BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char firstReq[]={0,6,0,2};
		os.print(firstReq);
		System.out.println("长度"+is.readLine());
		os.close();
		is.close();
		socket.close();
	*/	
/*		
		String url="http://www.panda.tv/ajax_chatinfo";
		String param="roomid=11010";
		String result = HttpRequest.sendGet(url,param);
		System.out.println("回响："+result+"\n");
		
		
		//获取返回的信息
		String danMuIP,danMuPort,danMUAuthtype,danMUSign,danMUTs,danMUMsg;
		int danMuRid,danMUAppid;
		JSONObject json= new JSONObject(result);
		JSONObject jsonData = (JSONObject) json.get("data");
		JSONArray jsonArray=jsonData.getJSONArray("chat_addr_list");
		String danMuAddress = jsonArray.getString(0);
		danMuIP=danMuAddress.split(":")[0];
		danMuPort=danMuAddress.split(":")[1];
		danMuRid = (int) jsonData.get("rid");
		danMUAppid = (int) jsonData.get("appid");
		danMUAuthtype = jsonData.getString("authtype");
		danMUSign = jsonData.getString("sign");
		danMUTs = jsonData.getString("ts");
		danMUMsg = "u:"+danMuRid+"@"+danMUAppid+"\nk:1\nt:300\nts:"+danMUTs+"\nsign:"+danMUSign+"\nauthtype:"+danMUAuthtype;
//		System.out.println(danMuRid);
		byte firstReq[]={0,6,0,2,0,0x61};
		byte content[]=danMUMsg.getBytes();
		byte sendMessage[]=new byte[firstReq.length+content.length];
		System.arraycopy(firstReq, 0, sendMessage, 0, firstReq.length);
		System.arraycopy(content, 0, sendMessage, firstReq.length, content.length);
		Socket socket = null;
		try {
//			socket = new Socket("127.0.0.1",6000);
			System.out.println(danMuIP+" "+danMuPort);
			socket=new Socket(danMuIP,Integer.parseInt(danMuPort));
			DataOutputStream os=new DataOutputStream(socket.getOutputStream()); 
			DataInputStream is=new DataInputStream(socket.getInputStream());
			for(int i=0;i<sendMessage.length;++i)
				System.out.print(Integer.toHexString(sendMessage[i])+" ");
			System.out.println("");
			os.write(sendMessage);
			//
			byte readData[]={0,0,0,0,0,0};

			System.out.println("读到"+is.read(readData)+"个字节");
			System.out.println("socket回响：");
			for(int i=0;i<6;++i)
				System.out.print(readData[i]+" ");
			System.out.println("");
			if(readData[0]==0&&readData[1]==6&&readData[2]==0&&readData[3]==6)
				System.out.println("连接弹幕服务器成功");
			else
				System.out.println("连接弹幕服务器失败");
			os.close();
			is.close();
			socket.close();
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
	}

}
