package Server;

import java.util.ArrayList;
import java.util.List;

import Server.Classes.RegisterNewPlayer;

public class RunGame {
	private List<RegisterNewPlayer> players;
	
	public RunGame(RegisterNewPlayer player) {
		
		this.players = new ArrayList<RegisterNewPlayer>();
		this.players.add(player);
	}
	
	public List<RegisterNewPlayer> GetPlayerList(){
		return this.players;
	}
	
	public void AddPlayer(RegisterNewPlayer player) {
		
		try {
			players.add(player);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
