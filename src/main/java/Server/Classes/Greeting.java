package Server.Classes;

public class Greeting {

	private final long id;
	private final String content;
	
	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}
	
	public String getGreeting() {
		return "ID: " + id + "; Greeting: " + content;
	}
}
