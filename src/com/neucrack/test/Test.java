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
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import com.iflytek.cloud.speech.ResourceUtil;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.neucrack.protocol.ConnectDanMuServer;
import com.neucrack.server.HttpRequest;

public class Test {
	SpeechSynthesizer mTts;
	@org.junit.Test
	public void test() throws UnknownHostException, IOException {
		SpeechUtility.createUtility(SpeechConstant.APPID+"=56dec8d1");

		//1.创建 SpeechSynthesizer 对象
		SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
		//2.合成参数设置，详见《iFlytek MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
		//设置合成音频保存位置（可自定义保存位置），保存在“./iflytek.pcm”
		//如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, null);
		//3.开始合成
		mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
	}
	//合成监听器
	private SynthesizerListener mSynListener = new SynthesizerListener(){
	//会话结束回调接口，没有错误时，error为null
	public void onCompleted(SpeechError error) {}
	//缓冲进度回调
	//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在
	public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
	//开始播放
	public void onSpeakBegin() {}
	//暂停播放
	public void onSpeakPaused() {}
	//播放进度回调
	//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在
	public void onSpeakProgress(int percent, int beginPos, int endPos) {}
	//恢复播放回调接口
	public void onSpeakResumed() {}
	};

}
