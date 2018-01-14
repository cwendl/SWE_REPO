package Server;

import Server.Classes.*;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Server.Classes.Greeting;
import Server.Classes.RegisterNewPlayer;

@RestController
public class ServerController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private RunGame game;
	
	@RequestMapping(value = "/greeting", method = RequestMethod.GET)
	public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
		String output = greeting.getGreeting();
		return output;
	}

	
	@RequestMapping(
			value = "/GameStatus", 
			method = RequestMethod.GET,
			produces = { "application/json", "application/xml" }
	)
	@ResponseBody
	public String GameStatus(HttpServletRequest request) {
		if (game == null)
			return GameStatus.GetGameStatus(request.getRemoteAddr(), 1, "Not enough players! Wait for other player to join!");
		else
			return "Default-state";
	}
	
	
	@RequestMapping(
			value = "/GameInformation",
			method = RequestMethod.GET,
			produces = { "application/json", "application/xml" }
	)
	public String GameInformation() {
		return "@GameInformation";
	}
	
	
	@RequestMapping(
			value = "/RegisterNewPlayerRequest",
			method = RequestMethod.POST,
			produces = { "application/json", "application/xml" },
			consumes = {"application/json"}
	)
	@ResponseBody
	public ResponseEntity<HttpStatus> RegisterNewPlayerRequest(
		@RequestBody RegisterNewPlayer newPlayer) {
		if(newPlayer != null) {
			if(game == null) {
				game = new RunGame(newPlayer);
				return ResponseEntity.ok(HttpStatus.OK);
			}

			else if(game.GetPlayerList().size() > 1)
				return ResponseEntity.ok(HttpStatus.CONFLICT);
			else if(game.GetPlayerList().get(0).GetClientIP().equals(newPlayer.GetClientIP()))
				return ResponseEntity.ok(HttpStatus.CONFLICT);
			try {
			game.GetPlayerList().add(newPlayer);
			System.out.println(game.GetPlayerList().size());
			return ResponseEntity.ok(HttpStatus.OK);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(HttpStatus.OK);
		}
			
		else
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
	}
	
	
	@RequestMapping(
			value = "/GameMapData",
			method = RequestMethod.POST,
			produces = { "application/json", "application/xml" },
			consumes = {"application/json"}
	)
	public ResponseEntity<String> GameMapData(
			@RequestBody GameMap gameMap) {
			
		if(gameMap != null) {
			if(game == null)
				return ResponseEntity.badRequest().body("No running Game!");
			
			return ResponseEntity.ok("GameMap accepted");
		}else {
			return ResponseEntity.badRequest().body("No GameMap found!");
		}
	}
	
	
	@RequestMapping(
			value = "/GameTurnData",
			method = RequestMethod.POST,
			produces = { "application/json", "application/xml" }
	)
	public String GameTurnData() {
		return "@GameTurnData";
	}
}
