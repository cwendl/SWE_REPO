package Client.Controller;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import Client.Model.GameMap;
import Client.Model.PlayerID;
import Client.Model.Tile;
import Client.View.GameView;


@SpringBootApplication
public class ClientMain {
	
	private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
	private static RestTemplate restTemplate;
	private static HttpHeaders httpHeaders;
	
	private static GameView view;
	private static GameMap map;
	private static PlayerID player;
	private static int position;
	private static int enemyCastlePosition;
	public static void main(String[] args) {

		HashMap<String, Object> props = new HashMap<>();
		props.put("server.port", 9999);
		
		
		player = new PlayerID("Spieler1","192.168.0.3","8080","http://localhost:");
        new SpringApplicationBuilder()
        .sources(ClientMain.class)
        .properties(props)
        .run(args);
		restTemplate = new RestTemplate();
		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		

	    log.info(restTemplate.getMessageConverters().toString());
		String s = restTemplate.getForObject(player.GetAddress() + player.GetPort() + "/greeting?name=" + player.GetPlayerName(), String.class);
		log.info(s);
		view = new GameView();
		view.Start(s);
		generateMap();
		runGame();
		
	}
	
	private static void generateMap() {
		if(map == null) {
			Tile[][] tileMapPart = new Tile[8][8];
			int tileTypeLocal = 0;
			int maxWater = ThreadLocalRandom.current().nextInt(4, 6 + 1);
			log.info("Max water tiles set to: " + maxWater);
			int maxMountain = ThreadLocalRandom.current().nextInt(3, 8 + 1);
			log.info("Max mountain tiles set to: " + maxMountain);
			log.info("Generating map now....");
			for(int y= 0; y<8; y++) {
				for(int x=0; x<8;x++) {
					if(y >3) {
						tileMapPart[x][y] = new Tile(3, false, false, 0);
						continue;
					}
					tileTypeLocal= ThreadLocalRandom.current().nextInt(0,2+1);
					switch(tileTypeLocal) {
					case 0:
						break;
					case 1:maxMountain--;
						break;
					case 2:maxWater--;
						break;
					default:
						break;
					}
					if(maxWater >=0 && maxMountain>=0)
						tileMapPart[x][y] = new Tile(tileTypeLocal, false, false, 0);
					else if(maxMountain < 0)
						if(maxWater < 0 || tileTypeLocal == 1)
							tileMapPart[x][y] = new Tile(0, false, false, 0);
						else
							tileMapPart[x][y] = new Tile(tileTypeLocal, false, false, 0);
					else if(maxWater <0)
						if(maxMountain <0 || tileTypeLocal == 2)
							tileMapPart[x][y] = new Tile(0, false, false, 0);
						else
							tileMapPart[x][y] = new Tile(tileTypeLocal, false, false, 0);
				}
			}
			map = new GameMap(tileMapPart);
			map.map[0][0].SetCastle(true);
			view.Draw(map, 0, 77);
			JSONObject mapPart = new JSONObject();
			try {
				mapPart.put("PlayerName",player.GetPlayerName());
				JSONObject gameData = new JSONObject();
				gameData.put("Datatype", "Map");
				JSONArray content = new JSONArray();
				for(int y=0; y<4;y++) {
					JSONObject rowNumber = new JSONObject();
					rowNumber.put("RowNumber", y);
					JSONArray columns = new JSONArray();
					for(int x=0;x<8;x++) {
						JSONObject column = new JSONObject();
						column.put("TileNumberX", x);
						column.put("TileType", map.GetTileTypeOn(x, y));
						columns.put(column);
					}

					rowNumber.put("Columns", columns);
					content.put(rowNumber);
						
				}
				gameData.put("Content", content);
				mapPart.put("GameData", gameData);
				log.info("JSON message object: " + mapPart);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			HttpEntity<String> entity = new HttpEntity<String>(mapPart.toString(),httpHeaders);
			String s = restTemplate.postForObject(player.GetAddress() + player.GetPort() +"/GameMapData", entity, String.class);
			
			//ResponseEntity<String> s = restTemplate.postForEntity("http://localhost:8080/GameMapData", new HttpEntity<Object>(mapPart.toString(), httpHeaders), String.class);
			log.info(s.toString());

		}

	}
	
	private static void updateMap() {

		if(map == null) {
			Tile[][] tileMapPart = new Tile[8][8];
			GameMap map = new GameMap(tileMapPart);
		}
		
		String s = restTemplate.getForObject(player.GetAddress() + player.GetPort() +"/GameInformation", String.class);
		log.info("Enemy Map: " + s);
		
		/** Generating enemy mappart by mirroring own map since server only replying with dummymap **/
		for(int y=4; y<8; y++) {
			for(int x=0; x<8;x++) {
				map.map[x][y] = map.map[x][y-4];

			}
		}
		/** assuming that enemy is on field(0,4) his castle will be there
		 *  normally the server would respond with the hole map and both player positions		
		 */
		log.warn("Server not finished! Mirroring map");
		enemyCastlePosition=40;
		view.Draw(map, 0, 40);
	}
	
	private static void runGame() {

		boolean run=true;
		while(run) {
			String s = restTemplate.getForObject(player.GetAddress() + player.GetPort() +"/GameStatus", String.class);
			log.info("Server response on /GameStatus: " + s);
			switch(s) {
			case "Wait": try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "Turn": position=calcTurn();
				break;
			case "NewData": updateMap();
				break;
			case "Winner": view.Finish(true);
				break;
			case "Looser": view.Finish(false);
				break;
			default:try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} break;
			}
			
		}
		
	}
	
	private static int calcTurn() {

		boolean treasure = true; //map.TreasureOn(position-((position/10)*10), position/10); 
		log.info("Treasure found : " + map.TreasureOn(position-((position/10)*10), position/10));
		
		if(!treasure) {
			/** algorithm for traveling to all plain fields
			 * dijkstra not really suitable
			 */
			
		}else {
			/** algorithm to travel to enemy castle 
			 * set up dijkstra **/

			/** calculate distance to enemy castle **/
			int goalPos = enemyCastlePosition;
			int distanceX, distanceY =0;
			/** using java integer truncation to get y value **/
			int gposY= goalPos/10;
			int pposY= position/10;
			distanceX = (goalPos-(gposY*10)) - (position-(pposY*10));
			distanceY= goalPos/10 - position/10;
			log.info("Approaching enemy castle. Goalposition : " +goalPos);
			log.info("Distance between player and enemycastle: in X: " + distanceX + " in Y: " + distanceY);
			for(int y=0; y<8;y++) {
				for(int x=0;x<8;x++) {
					if(distanceX < 0)
						if(x + distanceX <0)
							switch(map.GetTileTypeOn(x, y)) {
							case 0: map.SetWeightOf(x, y, 1);break;
							case 1: map.SetWeightOf(x, y, 4);break;
							case 2: map.SetWeightOf(x, y, 200);break;
							}
						else if((x + distanceX) == 0) {
							if((distanceY - y) > 0) {
								switch(map.GetTileTypeOn(x, y)) {
								case 0: map.SetWeightOf(x, y, 1);break;
								case 1: map.SetWeightOf(x, y, 4);break;
								case 2: map.SetWeightOf(x, y, 200);break;
								}
							}else
								map.SetWeightOf(x, y, 0);
						}else
							map.SetWeightOf(x, y, 200);
					else if(distanceX > 0) {
						if(distanceX - x >0)
							map.SetWeightOf(x, y, 200);
						else if((distanceX - x) == 0)
							map.SetWeightOf(x, y, 0);
						else
							switch(map.GetTileTypeOn(x, y)) {
							case 0: map.SetWeightOf(x, y, 1);break;
							case 1: map.SetWeightOf(x, y, 4);break;
							case 2: map.SetWeightOf(x, y, 200);break;
							}
					}else
						break;
					

				}

			}
			map.SetWeightOf((position/10)*10, position/10, 0);
			
}
				
			
		
		
		
		return 0;
	}
}
