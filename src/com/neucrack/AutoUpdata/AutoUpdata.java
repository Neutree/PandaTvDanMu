package com.neucrack.AutoUpdata;

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

		if(indexOfUpdateAddr==-1 || indexOfUPdateAddrEnd == -1)
			return null;
		if(result.substring(indexOfUpdateAddr, indexOfUPdateVersionEnd).equals(UPDATA_ADDRESS+VERSION))
			return null;
		return UPDATA_ADDRESS_WEBSITE+result.substring(indexOfUpdateAddr, indexOfUPdateAddrEnd);
	}
}
