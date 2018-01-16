package Client.Controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.client.RestTemplate;

import Client.Model.PlayerID;
import Client.View.GameView;


@SpringBootApplication
public class ClientMain {
	
	private static final Logger log = LoggerFactory.getLogger(ClientMain.class);
	
	private static GameView view;
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
		RestTemplate restTemplate = new RestTemplate();
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
