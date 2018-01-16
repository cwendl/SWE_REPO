package Client.View;


import Client.Model.GameMap;

public class GameView {
	
	public GameView() {}
	
	public void Start(String serverResponse) {
		System.out.println("SWE Game\n");
		System.out.println("Connecting to server...\n");
		System.out.println("Server response: " + serverResponse);
		System.out.println("\nGenerating map....\n");
	}
	
	public void Draw(GameMap gameMap, Integer position, Integer enemyPosition) {
		System.out.print("\033[H\033[2J");
		System.out.println("\nThe map:\n");
		for(int y=7; y>0; y--) {
			for(int x=0; x<8; x++) {
				if(position == x+y)
					System.out.print('P');
				else if(enemyPosition == x+y)
					System.out.print('E');
				else if(gameMap.CastleOn(x, y))
					System.out.print('C');
				else {
					switch(gameMap.GetTileTypeOn(x, y)) {
					case 0: System.out.print('o');
						break;
					case 1: System.out.print('M');
						break;
					case 2: System.out.print('~');
						break;
					default: System.out.print('X');
						break;
					}
				}
			}
		}
		System.out.println("Player on Tile : " + position);
		System.out.println("Enemy on Tile : " + enemyPosition);
	}
	
	public void Finish(boolean won) {
		if(won)
			System.out.println("You win the game!!!");
		else
			System.out.println("You've lost....");
	}
}
