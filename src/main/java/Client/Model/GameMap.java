package Client.Model;

public class GameMap {
	public Tile[][] gameMap;
	
	public GameMap(Tile[][] gameMap) {
		this.gameMap=gameMap;
	}
	
	public boolean CastleOn(Integer x, Integer y) {
		return gameMap[x][y].Castle;
	}
	
	public boolean TreasureOn(Integer x, Integer y) {
		return gameMap[x][y].Treasure;
	}
	
	public void SetWeightOf(Integer x, Integer y, Integer weight) {
		gameMap[x][y].Setweight(weight);
	}
}
