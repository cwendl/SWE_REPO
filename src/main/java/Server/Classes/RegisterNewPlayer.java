package Server.Classes;

public class RegisterNewPlayer {
	public String ClientIP;
	public String Port;
	public String PlayerName;
	
	public RegisterNewPlayer() {}
	
	public RegisterNewPlayer(String ClientIP, String Port, String PlayerName){
		this.ClientIP = ClientIP;
		this.Port = Port;
		this.PlayerName = PlayerName;
	}
	
	public String GetClientIP() {
		return this.ClientIP;
	}
	
	public void SetClientIP(String ClientIP) {
		this.ClientIP = ClientIP;
	}
	
	public String GetPort() {
		return this.Port;
	}
	
	public void SetPort(String Port) {
		this.Port = Port;
	}
	public String GetPlayername() {
		if(this.PlayerName != null)
			return this.PlayerName;
		else {
			this.SetPlayername(this.GetClientIP());
		}
		return this.GetClientIP();
	}
	
	public void SetPlayername(String PlayerName) {
		this.PlayerName = PlayerName;
	}
	
}
