package Server.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameMapData {
	public String PlayerName;
	public String GameData;
	
	public GameMapData() {}
	
	public GameMapData(String PlayerName, String GameData) {
		this.PlayerName = PlayerName;
		this.GameData = GameData;
	}
	
	public String GetPlayerName() {
		return this.PlayerName;
	}
	
	private void SetPlayerName(String PlayerName) {
		this.PlayerName = PlayerName;
	}
	
	public String GetGameData() {
		return this.GameData;
	}
	
	private void SetGameData(String GameData) {
		this.GameData = GameData;
	}
	
	public static Boolean CheckMap(String GameData) {
		//TODO Extract Map into Array to Check
		JSONObject obj;
		try {
			obj = new JSONObject(GameData);
			try {
				JSONArray arrayMap = obj.getJSONArray("Content");
				obj = arrayMap.getJSONObject(0);
				JSONArray contentMap = obj.getJSONArray("Columns");
				for(int i = 0; i< contentMap.length(); i++) {
					System.out.println(contentMap.getJSONObject(i));
				}
				String arrayMapString = new String(arrayMap.toString());
				System.out.println(arrayMapString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(GameData);
		return true;
	}
}
