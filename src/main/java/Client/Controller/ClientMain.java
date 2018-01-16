package Client.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import Client.Model.GameMap;
import Client.Model.PlayerID;
import Client.Model.Tile;
import Client.View.GameView;
import antlr.collections.List;


@SpringBootApplication
public class ClientMain {
	
	private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
	private static RestTemplate restTemplate;
	private static HttpHeaders httpHeaders;
	
	private static GameView view;
	private static GameMap map;
	private static PlayerID player;
	public static void main(String[] args) {
		/*
		 *
		 * 		->stellt verbindung zu server her mittels global instantierter playerID
				->ruft startmethode von clientview auf
				-> ruft methode zur mapgenerierung auf
				-> ruft methode zum spielablauf auf
		 */
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
		
		/*
	    java.util.List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();    
	    messageConverters.add(new FormHttpMessageConverter());
	    messageConverters.add(new StringHttpMessageConverter());
	    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	    jsonConverter.setObjectMapper(new ObjectMapper());
	    java.util.List<MediaType> mediaTypeList= new ArrayList<MediaType>();
	    mediaTypeList.add(new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
	    mediaTypeList.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
	    jsonConverter.setSupportedMediaTypes(mediaTypeList);
	    messageConverters.add(jsonConverter);
	    restTemplate.setMessageConverters(messageConverters);
	    
	    */
	    log.info(restTemplate.getMessageConverters().toString());
		String s = restTemplate.getForObject("http://localhost:8080/greeting?name=" + player.GetPlayerName(), String.class);
		log.info(s);
		view = new GameView();
		view.Start(s);
		generateMap();
		runGame();
		
	}
	
	private static void generateMap() {
		/*
		 * 		->generiert die halbe karte (obere arrays)
					-> zufallsfunktionen für verschiedene maps (pfusch reicht)
				-> legt startposition mit burg fest
					-> instantiiert globale positions variable (integer)
				-> sendet map an server über globale playerID
				//min 3berge min 4 wasser ->5 wasser und 8berge max
		 */
		if(map == null) {
			Tile[][] tileMapPart = new Tile[8][8];
			int tileTypeLocal = 0;
			int maxWater = ThreadLocalRandom.current().nextInt(4, 6 + 1);
			log.info("Max water tiles set to: " + maxWater);
			int maxMountain = ThreadLocalRandom.current().nextInt(3, 8 + 1);
			log.info("Max mountain tiles set to: " + maxMountain);
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
			String s = restTemplate.postForObject("http://localhost:8080/GameMapData", entity, String.class);
			
			//ResponseEntity<String> s = restTemplate.postForEntity("http://localhost:8080/GameMapData", new HttpEntity<Object>(mapPart.toString(), httpHeaders), String.class);
			log.info(s.toString());

		}

	}
	
	private static void updateMap() {
		/*
		 * 		-> instantiiert globale map (falls nicht vorhanden)
				-> holt sich mapdaten von server
					-> speichert gegnerposition in map als burg der zweiten hälfte
				-> schreibt mapdaten in globale map
		 */
		if(map == null) {
			Tile[][] tileMapPart = new Tile[8][8];
			GameMap map = new GameMap(tileMapPart);
		}
		
		String s = restTemplate.getForObject("http://localhost:8080/GameInformation", String.class);
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
		view.Draw(map, 0, 40);
	}
	
	private static void runGame() {
		/*
		 *		->while schleife
				->erfragt status von server
				->ruft methode zur schrittberechnung auf ||
				->ruft methode zum updaten der map auf
				->sendet schritt an server
				->ruft draw methode von clientview auf
				---- erfragt status bis gewonnen/verloren -> kommt von server 
		 */
		updateMap();
		
	}
	
	private int calcTurn() {
		/*
		 *		->liest positionsvariable
				->prüft ob schatz vorhanden
					->falls ja: berechnet mit for schleife besten weg zu gegnerischer burg
					->andernfalls:
						->berechnet gewichte der map mittels dijkstra algorithmus
							-> legt dabei gewichte direkt in eigener methode fest (schleifen)
							->merkt sich niedrigstes gewicht in nextpos variable
							->besuchte felder oder wasserfelder werden mit -1 belegt (für unendlich) 
							  land: 1 berg: 4(je nach angabe)
							->zielt darauf ab beste route um alle felder zu besuchen zu finden (nur in eigener kartenhälfte!)
				-> wenn beste route gefunden
					->retourniert nextpos als int 
		 */
		
		return 0;
	}
}
