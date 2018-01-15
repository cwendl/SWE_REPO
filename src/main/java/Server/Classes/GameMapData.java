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
		String[][] map = new String[4][8];
		try {
			obj = new JSONObject(GameData);
			try {
				JSONArray arrayMap = obj.getJSONArray("Content");
				if (arrayMap.length() != 4)
					return false;
				for(int i = 0; i < arrayMap.length(); i++) {
					obj = arrayMap.getJSONObject(i);
					System.out.println("RownumberY: " + obj.getInt("RowNumber"));
					JSONArray tmpcontentMap = obj.getJSONArray("Columns");
					if(tmpcontentMap.length() != 8)
						return false;
					for(int j = 0; j< tmpcontentMap.length(); j++) {
						JSONObject tmpobj = tmpcontentMap.getJSONObject(j);
						try {
						if(tmpobj.getInt("TileNumberX") <0 || tmpobj.getInt("TileNumberX") >7)
							return false;
						if(tmpobj.getInt("TileType") <0 || tmpobj.getInt("TileType") >2)
							return false;
						}catch(JSONException e) {
							e.printStackTrace();
						}
						System.out.println(tmpcontentMap.getJSONObject(j));
					}
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
