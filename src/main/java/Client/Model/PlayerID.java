package Client.Model;

public class PlayerID {
	
	public String PlayerName;
	public String IP;
	public String Port;
	public String Address;
	
	public PlayerID (String PlayerName, String IP, String Port, String Address) {
		this.PlayerName=PlayerName;
		this.IP=IP;
		this.Port=Port;
		this.Address=Address;
	}
	
	public String GetPlayerName() {
		return this.PlayerName;
	}
	
	public String GetIP() {
		return this.IP;
	}
	
	public String GetPort() {
		return this.Port;
	}
	
	public String GetAddress() {
		return this.Address;
	}
}
