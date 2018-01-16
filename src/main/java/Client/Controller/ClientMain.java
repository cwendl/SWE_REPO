package Client.Controller;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.client.RestTemplate;

import Client.Model.GameMap;
import Client.Model.PlayerID;
import Client.Model.Tile;
import Client.View.GameView;


@SpringBootApplication
public class ClientMain {
	
	private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
	private static RestTemplate restTemplate;
	
	private static GameView view;
	private static GameMap map;
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
		
		
		PlayerID player = new PlayerID("Spieler1","192.168.0.3","8080","http://localhost:");
        new SpringApplicationBuilder()
        .sources(ClientMain.class)
        .properties(props)
        .run(args);
		restTemplate = new RestTemplate();
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
			view.Draw(map, 0, 77);
			//String s = restTemplate.patchForObject("http://localhost:8080/GameMapData", request, String.class);
			//String s = restTemplate.getForObject("http://localhost:8080/GameMapData", String.class);
		}

	}
	
	private static void updateMap() {
		/*
		 * 		-> instantiiert globale map (falls nicht vorhanden)
				-> holt sich mapdaten von server
					-> speichert gegnerposition in map als burg der zweiten hälfte
				-> schreibt mapdaten in globale map
		 */
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
