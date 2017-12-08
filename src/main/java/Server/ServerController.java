package Server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
		String output = greeting.getGreeting();
		return output;
	}

	@RequestMapping(value = "/GameStatus", method = RequestMethod.GET)
	public String GameStatus() {
		return "@GameStatust";
	}
	
	@RequestMapping(value = "/GameInformation", method = RequestMethod.GET)
	public String GameInformation() {
		return "@GameInformation";
	}
	
	@RequestMapping(value = "/RegisterNewPlayerRequest", method = RequestMethod.POST)
	public String RegisterNewPlayerRequest() {
		return "@RegisterNewPlayerRequest";
	}
	
	@RequestMapping(value = "/GameMapData", method = RequestMethod.POST)
	public String GameMapData() {
		return "@GameMapData";
	}
	
	@RequestMapping(value = "/GameTurnData", method = RequestMethod.POST)
	public String GameTurnData() {
		return "@GameTurnData";
	}
}
