package Server.Classes;

public class GameMap {
	String PlayerName;
	String GameData;
	
	public GameMap() {}
	
	public GameMap(String PlayerName, String GameData) {
		this.PlayerName = PlayerName;
		this.GameData = GameData;
	}
	
	public String GetPlayerName() {
		return this.PlayerName;
	}
	
	public String GetGameData() {
		return this.GameData;
	}
}
