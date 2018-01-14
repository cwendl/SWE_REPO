package Server.Classes;

public class RegisterNewPlayer {
	public String ClientIP;
	public String Port;
	public String Playername;
	
	public RegisterNewPlayer() {}
	
	public RegisterNewPlayer(String ClientIP, String Port, String Playername){
		this.ClientIP = ClientIP;
		this.Port = Port;
		this.Playername = Playername;
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
		if(this.Playername != null)
			return this.Playername;
		else {
			this.SetPlayername(this.GetClientIP());
		}
		return this.GetClientIP();
	}
	
	public void SetPlayername(String Playername) {
		this.Playername = Playername;
	}
	
}
