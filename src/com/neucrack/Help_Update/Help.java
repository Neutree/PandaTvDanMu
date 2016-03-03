package com.neucrack.Help_Update;

public class Help {
	private String mHelpMessage="使用说明：\n打开软件，输入房间号，然后键盘点击确定或者鼠标点击播放按钮，\n连接成功后下面会提示成功，按钮变成停止按钮\r\n"
				+"连接成功后，暂停按钮表示暂停弹幕自动滚屏，暂停自动滚动后暂停按钮会变成继续播放按钮\r\n"
				+"在弹幕列表中滚动会竖直滚动，按住ctrl键滚动可以横滚，方便查看长弹幕\r\n";
	private String mAboutMaker="程序开源，托管在github上：\nhttps://github.com/Neutree/PandaTvDanMu\r\n"
				+ " 最新版下载地址：\r\nhttps://github.com/Neutree/PandaTvDanMu/releases \r\n注意：留意自己是下载的源码还是可执行程序";

	public Help() {
		super();
	}

	public String getmHelpMessage() {
		return mHelpMessage;
	}

	public String getmAboutMaker() {
		return mAboutMaker;
	}

	public void setmAboutMaker(String mAboutMaker) {
		this.mAboutMaker = mAboutMaker;
	}

	public void setmHelpMessage(String mHelpMessage) {
		this.mHelpMessage = mHelpMessage;
	}
	
}
