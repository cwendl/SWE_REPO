package Server.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class GameTurnData {

	public String PlayerName;
	public String GameTurnData;
	
	public GameTurnData(){}
	
	public GameTurnData(String PlayerName, String GameTurnData) {
		this.PlayerName=PlayerName;
		this.GameTurnData=GameTurnData;
	}
	
	public String GetPlayerName() {
		return this.PlayerName;
	}
	
	public void SetPlayerName(String PlayerName) {
		this.PlayerName=PlayerName;
	}
	
	public String GetGameTurnData() {
		return this.GameTurnData;
	}
	
	public void SetGameTurnData() {
		this.GameTurnData=GameTurnData;
	}
	
	public static boolean CheckTurnData(String GameTurnData) {
		try {
			JSONObject obj = new JSONObject(GameTurnData);
			if(obj.getInt("TargetX") <0 || obj.getInt("TargetX") >7) {
				return false;
			}else if(obj.getInt("TargetY") <0 || obj.getInt("TargetY") >7)
				return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
		
	}
}
