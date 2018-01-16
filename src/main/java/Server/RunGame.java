package Server;

import java.util.ArrayList;
import java.util.List;

import Database_package.HibernateMain;
import Server.Classes.RegisterNewPlayer;

public class RunGame {
	private List<RegisterNewPlayer> players;
	private RegisterNewPlayer winner, looser;
	private boolean end;
	
	public RunGame(RegisterNewPlayer player) {
		
		this.players = new ArrayList<RegisterNewPlayer>();
		this.players.add(player);
	}
	
	public List<RegisterNewPlayer> GetPlayerList(){
		return this.players;
	}
	
	public RegisterNewPlayer GetLooser() {
		return looser;
	}

	private void SetLooser(RegisterNewPlayer looser) {
		this.looser = looser;
	}
	
	public RegisterNewPlayer GetWinner() {
		return winner;
	}

	private void SetWinner(RegisterNewPlayer winner) {
		this.winner = winner;
	}

	public void AddPlayer(RegisterNewPlayer player) {
		
		try {
			players.add(player);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void EndGame() {
		this.end=true;
	}
	
	private void WaitOnPlayer(RegisterNewPlayer player) {
		
	}
	
	public void run() {
		end = false;
		HibernateMain database = new HibernateMain();
		while(!end) {
			
		}
		
	}
}
