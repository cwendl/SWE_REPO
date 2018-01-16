package Client.Controller;

public class ClientMain {
	public static void main(String[] args) {
		/*
		 * 		->stellt verbindung zu server her mittels global instantierter playerID
				->ruft startmethode von clientview auf
				-> ruft methode zur mapgenerierung auf
				-> ruft methode zum spielablauf auf
		 */
	}
	
	private void generateMap() {
		/*
		 * 		->generiert die halbe karte (obere arrays)
					-> zufallsfunktionen für verschiedene maps (pfusch reicht)
				-> legt startposition mit burg fest
					-> instantiiert globale positions variable (integer)
				-> sendet map an server über globale playerID
		 */
	}
	
	private void runGame() {
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
