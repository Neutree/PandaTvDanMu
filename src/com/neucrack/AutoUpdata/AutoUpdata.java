package com.neucrack.AutoUpdata;

import org.omg.CORBA.INTERNAL;

import com.neucrack.server.HttpRequest;

public class AutoUpdata {
	
	public static final String UPDATA_ADDRESS_WEBSITE = "https://github.com";
	public static final String UPDATA_ADDRESS = "/Neutree/PandaTvDanMu/releases/download/";
	public static final String RELEASE_ADDRESS = "https://github.com/Neutree/PandaTvDanMu/releases";
	
	public static final String VERSION  = "V0.2.6";
	
	public static  String DetectLatestVersion(){
		String result = HttpRequest.sendGet(RELEASE_ADDRESS, null);
		int indexOfUpdateAddr = result.indexOf(UPDATA_ADDRESS);
		int indexOfUPdateAddrEnd = result.indexOf("\"", indexOfUpdateAddr);
		int indexOfUPdateVersion = result.indexOf("download/", indexOfUpdateAddr);
		int indexOfUPdateVersionEnd = result.indexOf("/", indexOfUPdateVersion+9);

		int version1 = Integer.parseInt(result.substring(indexOfUPdateVersion+10, result.indexOf(".", indexOfUPdateVersion)));
		int version2 = Integer.parseInt(result.substring(result.indexOf(".", indexOfUPdateVersion)+1, result.indexOf(".", result.indexOf(".", indexOfUPdateVersion)+1)));
		int version3 = Integer.parseInt(result.substring(result.indexOf(".", result.indexOf(".", indexOfUPdateVersion)+1)+1, result.indexOf("/", result.indexOf(".", result.indexOf(".", indexOfUPdateVersion)+1)+1)));
		
		int curVersion1 = Integer.parseInt(VERSION.substring(1, VERSION.indexOf(".")));
		int curVersion2 = Integer.parseInt(VERSION.substring( VERSION.indexOf(".")+1, VERSION.indexOf(".",VERSION.indexOf(".")+1)));
		int curVersion3 = Integer.parseInt(VERSION.substring( VERSION.indexOf(".",VERSION.indexOf(".")+1)+1));
		
		if(indexOfUpdateAddr==-1 || indexOfUPdateAddrEnd == -1)//没找到信息
			return null;
		if(version1*100+version2*10+version3 <= curVersion1*100+curVersion2*10+curVersion3)//没有新版，无需更新
			return null;
		return UPDATA_ADDRESS_WEBSITE+result.substring(indexOfUpdateAddr, indexOfUPdateAddrEnd);
	}
}
