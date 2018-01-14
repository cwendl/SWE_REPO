package Server.Classes;

public class GameStatus {
	
	public static String GetGameStatus(String playerName, Integer statusType, String moreInfo) {
		switch(statusType) {
			case 1:
				return GameStatus.SetupJson(playerName, GameStatus.Wait(), moreInfo);
			case 2:
				return GameStatus.SetupJson(playerName, GameStatus.Turn(), moreInfo);
			case 3:
				return GameStatus.SetupJson(playerName, GameStatus.NewData(), moreInfo);
			default:
				return GameStatus.SetupJson(playerName, GameStatus.Error(), moreInfo);
		}
	}
	
	private static String SetupJson(String playerName, String statusType, String moreInfo) {
		return new String("{\"title\": \"GameStatus\""
						+",\"type\": \"Response\""
						+ ", \"properties\": {"
						+ "\"Playername\": \"" + playerName + "\","
						+ statusType + ","
						+ "\"MoreInfo\": \"" + moreInfo + "\"}}");
	}
	private static String Wait() {
		return new String("\"StatusType\": \"Wait\"");
	}
	
	private static String Turn() {
		return new String("\"StatusType\": \"Turn\"");
	}
	
	private static String NewData() {
		return new String("\"StatusType\": \"NewData\"");
	}
	
	private static String Error() {
		return new String("\"StatusType\": \"Error\"");
	}
}
