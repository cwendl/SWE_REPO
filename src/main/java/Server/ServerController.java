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
	private static int tempcount=0;
	
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
		/*if (game == null)
			return GameStatus.GetGameStatus(request.getRemoteAddr(), 1, "Not enough players! Wait for other player to join!");
		else {*/
			switch(tempcount) {
			case 0: tempcount++; 
				return "NewData";
			case 1: tempcount++;
				return "Wait";
			case 2: tempcount=tempcount-2;
				return "Turn";
			default: tempcount=0; 
				return "Wait";
			}
		//}
	}
	
	
	@RequestMapping(
			value = "/GameInformation",
			method = RequestMethod.GET,
			produces = { "application/json", "application/xml" }
	)
	public ResponseEntity<String> GameInformation() {
		return ResponseEntity.ok("\"{\\\"Datatype\\\": \\\"Map\\\",\\\"Content\\\": [{\\\"RowNumber\\\": 0,\\\"Columns\\\": [{\\\"TileNumberX\\\": 0,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 1,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 2,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 3,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 4,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 5,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 6,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 7,\\\"TileType\\\": 0}]},{\\\"RowNumber\\\": 1,\\\"Columns\\\": [{\\\"TileNumberX\\\": 0,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 1,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 2,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 3,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 4,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 5,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 6,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 7,\\\"TileType\\\": 0}]},{\\\"RowNumber\\\": 2,\\\"Columns\\\": [{\\\"TileNumberX\\\": 0,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 1,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 2,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 3,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 4,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 5,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 6,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 7,\\\"TileType\\\": 0}]},{\\\"RowNumber\\\": 3,\\\"Columns\\\": [{\\\"TileNumberX\\\": 0,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 1,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 2,\\\"TileType\\\": 1},{\\\"TileNumberX\\\": 3,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 4,\\\"TileType\\\": 0},{\\\"TileNumberX\\\": 5,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 6,\\\"TileType\\\": 2},{\\\"TileNumberX\\\": 7,\\\"TileType\\\": 0}]}]}\"");
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
			produces = { "application/json", "application/xml" }
	)
	public ResponseEntity<String> GameMapData(
			/*@RequestBody GameMapData gameMap*/) {
		/*	
		if(gameMap != null) {
			if(game == null)
				return ResponseEntity.badRequest().body("No running Game!");
			if(!GameMapData.CheckMap(gameMap.GetGameData()))
				return ResponseEntity.badRequest().body("Map Error! Map does not meet the requirements!");
			else {
				//TODO Add Mappart to game
				return ResponseEntity.ok("GameMap accepted");
			}
		}else {
			return ResponseEntity.badRequest().body("No GameMap found!");
		}
		*/
		return ResponseEntity.ok("GameMap accepted");
	}
	
	
	@RequestMapping(
			value = "/GameTurnData",
			method = RequestMethod.POST,
			produces = { "application/json", "application/xml" },
			consumes = {"application/json"}
	)
	public ResponseEntity<String> GameTurnData(
			@RequestBody GameTurnData turnData) {
		if(game == null)
			return ResponseEntity.badRequest().body("No running Game!");
		else if(turnData == null){
			return ResponseEntity.badRequest().body("No TurnData found!");
		}
		else if(!GameTurnData.CheckTurnData(turnData.GetGameTurnData()))
			return ResponseEntity.badRequest().body("TurnData error! TurnData does not meet requirements");
		else
			return ResponseEntity.ok().body("TurnData accepted!");
	}
}
