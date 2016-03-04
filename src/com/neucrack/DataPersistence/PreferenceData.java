package com.neucrack.DataPersistence;

import java.util.prefs.Preferences;

public class PreferenceData {
	private final String KEY_TRANSPARENTVALUE="transparentValue";
	public final static int MAXTRASPARENTVALUE=90;
	
	private final String KEY_ISSAVEROOMID="isSaveRoomID";
	private final String KEY_ROOMID="roomID";
	public final static String DEFAULT_ROOMID="313180";
	
	Preferences prefs; 
	
	public PreferenceData() {
		prefs = Preferences.userRoot().node(this.getClass().getName());
	}

	public boolean SaveTransparentValue(int value){
		prefs.putInt(KEY_TRANSPARENTVALUE, value);
		return true;
	}

	public int getmTransparentValue() {
		return prefs.getInt(KEY_TRANSPARENTVALUE,45);
	}
	public boolean SaveIsSaveRoomID(boolean isSaveRoomID){
		prefs.putBoolean(KEY_ISSAVEROOMID, isSaveRoomID);
		return true;
	}
	public boolean IsSaveRoomID(){
		return prefs.getBoolean(KEY_ISSAVEROOMID,false);
	}
	
	public boolean SaveRoomID(String roomID){
		prefs.put(KEY_ROOMID, roomID);
		return true;
	}
	public String GetRoomID(){
		return prefs.get(KEY_ROOMID,DEFAULT_ROOMID);
	}
}
